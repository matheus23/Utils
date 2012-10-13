package org.matheusdev.noises;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.matheusdev.PosIterationCallback;
import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.interpolation.FloatInterpolationFunc;
import org.matheusdev.util.GifSequenceWriter;
import org.matheusdev.util.matrix.MatrixNf;

/**
 * @author matheusdev
 *
 */
public class SimplexNoiseN {

	protected static SimplexNoiseLayerN[] layersFromOctaves(final int octaves, final int smoothness, final int increase, final Random rand, final FloatInterpolation interpolator, final int... dimensions) {
		SimplexNoiseLayerN[] layers = new SimplexNoiseLayerN[octaves];
		int v = smoothness;
		for (int i = 0; i < octaves; i++) {
			layers[i] = new SimplexNoiseLayerN(v, rand, interpolator, dimensions);
			v *= increase;
		}
		return layers;
	}

	protected static float[] importancesFromOctaves(final int octaves, final int increase) {
		float[] importances = new float[octaves];
		float v = 1;
		for (int i = 0; i < octaves; i++) {
			importances[i] = v;
			v *= increase;
		}
		return importances;
	}

	protected final MatrixNf values;

	public SimplexNoiseN(final int octaves, final Random rand, final FloatInterpolation interpolator, final int... dimensions) {
		this(layersFromOctaves(octaves, 2, 2, rand, interpolator, dimensions), importancesFromOctaves(octaves, 2), rand, true, dimensions);
	}

	public SimplexNoiseN(final int octaves, final int smoothness, final int smoothnessincrease, final int importanceincrease, final Random rand, final FloatInterpolation interpolator, final int... dimensions) {
		this(layersFromOctaves(octaves, smoothness, smoothnessincrease, rand, interpolator, dimensions), importancesFromOctaves(octaves, importanceincrease), rand, true, dimensions);
	}

	public SimplexNoiseN(SimplexNoiseLayerN[] layers, float[] importances, final Random rand, boolean threaded, final int... dimensions) {
		values = new MatrixNf(dimensions);

		if (threaded) {
			ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			for (int i = 0; i < layers.length; i++) {
				final SimplexNoiseLayerN layerToGen = layers[i];
				executor.execute(new Runnable() {
					@Override
					public void run() {
						layerToGen.gen();
					}
				});
			}

			executor.shutdown();
			try {
				executor.awaitTermination(30, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 1; i < layers.length; i++) {
			final float importance = importances[i];
			if (!threaded) {
				layers[i].gen();
			}
			final MatrixNf vals = layers[i].get();

			final int[] offsets = new int[vals.getDimensions().length];
			final int[] newPos = new int[offsets.length];

			for (int j = 0; j < offsets.length; j++) {
				offsets[j] = Math.abs(rand.nextInt() % 174174);
			}

			vals.each(new PosIterationCallback() {
				@Override
				public void call(int[] pos) {
					for (int i = 0; i < newPos.length; i++) {
						newPos[i] = (pos[i] + offsets[i]) % vals.getDimensions()[i];
					}
					values.set(values.get(pos) + vals.get(newPos) * importance, pos);
				}
			});

			for (int j = 0; j < vals.values.length; j++) {
				values.values[j] += vals.values[j] * importance;
			}
		}

		float totalImportance = 0;
		for (float f : importances) {
			totalImportance += f;
		}

		for (int i = 0; i < values.values.length; i++) {
			values.values[i] /= totalImportance;
		}
	}

	public MatrixNf get() {
		return values;
	}

	protected static int toRGB(int r, int g, int b, int a) {
		int rgb = a;
		rgb = (rgb << 8) + (r & 0xFF);
		rgb = (rgb << 8) + (g & 0xFF);
		rgb = (rgb << 8) + (b & 0xFF);
		return rgb;
	}

	public static void main(String[] args) {
		final int[] dims = { 64, 256, 256 };
		final int tilex = 1;
		final int tiley = 1;
		System.out.println("Creating.");
		long now = System.currentTimeMillis();
		final Random rand = new Random();
		final FloatInterpolation interpolator = new FloatInterpolationFunc() {
			@Override
			protected float func(float t) {
				return 3*t*t - 2*t*t*t;
			}
		};
		final MatrixNf content = new SimplexNoiseN(new SimplexNoiseLayerN[] {
				new SimplexNoiseLayerN(64, rand, interpolator, dims),
				new SimplexNoiseLayerN(32, rand, interpolator, dims),
				new SimplexNoiseLayerN(8, rand, interpolator, dims),
				new SimplexNoiseLayerN(4, rand, interpolator, dims)
		}, new float[] {
				64,
				32,
				8,
				4
		}, rand, true, dims).get();
		long then = System.currentTimeMillis();

		// Creating plane: (outdated?)
		/*
		int[][] plane = new int[dims[1]][dims[2]];
		int x0 = 0;
		int y0 = 0;
		int x1 = dims[1]-1;
		int y1 = dims[2]-1;
		plane[x0][y0] = Math.abs(rand.nextInt() % dims[0]);
		plane[x1][y0] = Math.abs(rand.nextInt() % dims[0]);
		plane[x1][y1] = Math.abs(rand.nextInt() % dims[0]);
		plane[x0][y1] = Math.abs(rand.nextInt() % dims[0]);
		int edge00 = plane[x0][y0];
		int edge10 = plane[x1][y0];
		int edge11 = plane[x1][y1];
		int edge01 = plane[x0][y1];
		for (int x = 0; x < plane.length; x++) {
			for (int y = 0; y < plane[x].length; y++) {
				if (!( (x == x0 && y == y0)
					|| (x == x1 && y == y0)
					|| (x == x1 && y == y1)
					|| (x == x0 && y == y1) )) {
					float tx = x / (Math.abs(x0-x1));
					float ty = y / (Math.abs(y0-y1));
					int val0 = Math.round(interpolator.interpolate(tx, edge00, 0, edge10, 1));
					int val1 = Math.round(interpolator.interpolate(tx, edge01, 0, edge11, 1));
					plane[x][y] = Math.round(interpolator.interpolate(ty, val0, 0, val1, 1));
				}
			}
		}*/
		System.out.println("Creation finished (" + (then-now) + " ms).");
		System.out.println("Converting to Image.");

		BufferedImage[] imgs = new BufferedImage[dims[0]];
		for (int layer = 0; layer < dims[0]; layer++) {
			BufferedImage img = new BufferedImage(dims[1]*tilex, dims[2]*tiley, BufferedImage.TYPE_INT_ARGB);
			for (int x = 0; x < dims[1]*tilex; x++) {
				for (int y = 0; y < dims[2]*tiley; y++) {
					//int greyscale = (int)(((content.get((layer + plane[x][y]) % dims[0], x%dims[1], y%dims[2])+1)*0.5) * 255);
					//img.setRGB(x, y, toRGB(greyscale, greyscale, greyscale, 255));
					int col = (int)(((content.get((layer) % dims[0], x%dims[1], y%dims[2])+1)*0.5) * 100000);
					img.setRGB(x, y, col | 0xFF000000);
				}
			}
			imgs[layer] = img;
		}
		now = System.currentTimeMillis();
		System.out.println("Converting finished (" + (now-then) + " ms).");
		String directory = "simplex_noise";
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		String filename = directory + "/" + "image";
		String suffix = ".gif";

		int i = 0;
		File file;
		do {
			i++;
			file = new File(filename + i + suffix);
		} while (file.exists());
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		then = System.currentTimeMillis();
		System.out.println("Writing to file.");
		ImageOutputStream stream = null;
		try {
			stream = new FileImageOutputStream(file);
			GifSequenceWriter writer = new GifSequenceWriter(stream, BufferedImage.TYPE_INT_RGB, 64, true);
			for (BufferedImage img: imgs) {
				writer.writeToSequence(img);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		now = System.currentTimeMillis();
		System.out.println("Finished writing (to " + filename + i + suffix + ")(" + (now-then) + " ms).");
	}

}
