package org.matheusdev.noises;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.matheusdev.PosIterationCallback;
import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.interpolation.FloatInterpolationFunc;
import org.matheusdev.util.matrix.MatrixNf;

/**
 * @author matheusdev
 *
 */
public class SimplexNoiseLayerN {

	protected static boolean getBit(int integer, int bit) {
		return ((integer & (1 << bit)) >> bit) == 0 ? false : true;
	}

	protected final MatrixNf values;
	protected final int density;
	protected final Random rand;
	protected final FloatInterpolation interpolator;

	public SimplexNoiseLayerN(final int density, final Random rand, final FloatInterpolation interpolator, final int... dimensions) {
		values = new MatrixNf(dimensions);
		this.density = density;
		this.rand = rand;
		this.interpolator = interpolator;
	}

	public SimplexNoiseLayerN gen() {
		final int[] dims = values.getDimensions();

		final SimplexNoiseLayerN that = this;

		each(new PosIterationCallback() {
			@Override
			public void call(int[] pos) {
				that.values.set(that.rand.nextBoolean() ? -1f : 1f, pos);
			}
		}, new int[dims.length], dims.length, 0, new int[dims.length], dims, density);

		// Defined here for pooling:
		final int[] nextPos = new int[dims.length];

		each(new PosIterationCallback() {
			@Override
			public void call(int[] pos) {
				// Increased next position (to interpolate with the current one):
				for (int ii = 0; ii < nextPos.length; ii++) {
					nextPos[ii] = (pos[ii] + density);
				}

				final int[][] edges = buildEdges(pos, nextPos);

				each(new PosIterationCallback() {
					@Override
					public void call(int[] pos) {
						that.values.set(
								interpolate(pos, edges, values, interpolator, dims.length-1),
								pos);
					}
				}, new int[dims.length], dims.length, 0, pos, nextPos, 1);
			}
		}, new int[dims.length], dims.length, 0, new int[dims.length], dims, density);
		return this;
	}

	protected static float interpolate(int[] point, int[][] edges, MatrixNf grid, FloatInterpolation interpol, int dimension) {
		if (edges.length < 2) {
			throw new IllegalArgumentException("edges.length < 0");
		}
		if (edges.length == 2) {
			int edgeDist = Math.abs(edges[0][dimension]-edges[1][dimension]);
			int pointDist = Math.abs(point[dimension]-edges[0][dimension]);
			float t = (float)pointDist / (float)edgeDist;

			int[] point0 = new int[edges[0].length];
			for (int i = 0; i < point0.length; i++) {
				point0[i] = edges[0][i]%grid.getDimensions()[i];
			}
			int[] point1 = new int[edges[1].length];
			for (int i = 0; i < point1.length; i++) {
				point1[i] = edges[1][i]%grid.getDimensions()[i];
			}

			return interpol.interpolate(t, grid.get(point0), 0, grid.get(point1), 1);
		} else {
			int halfLen = edges.length/2;
			int[][] half1 = new int[halfLen][grid.getDimensions().length];
			System.arraycopy(edges, 0, half1, 0, halfLen);
			int[][] half2 = new int[halfLen][grid.getDimensions().length];
			System.arraycopy(edges, halfLen, half2, 0, halfLen);

			int edgeDist = Math.abs(half1[0][dimension]-half2[0][dimension]);
			int pointDist = Math.abs(half1[0][dimension]-point[dimension]);
			float t = (float)pointDist / (float)edgeDist;

			return interpol.interpolate(t,
					interpolate(point, half1, grid, interpol, dimension-1), 0,
					interpolate(point, half2, grid, interpol, dimension-1), 1);
		}

	}

	protected static int[][] buildEdges(int[] start, int[] end) {
		final int dims = start.length; // Dimensions
		int[][] edges = new int[power(2, dims)][dims];

		for (int i = 0; i < edges.length; i++) {
			for (int dim = 0; dim < dims; dim++) {
				if (!getBit(i, dim)) { // Bit is 0
					edges[i][dim] = start[dim];
				} else {
					edges[i][dim] = end[dim];
				}
			}
		}

		return edges;
	}

	protected static int power(int n, int exp) {
		if (exp < 0) {
			throw new IllegalArgumentException("exp < 1");
		}
		if (exp == 0) {
			return 1;
		} else {
			int result = power(n, exp-1);
			return n * result;
		}
	}

	public MatrixNf get() {
		return values;
	}

	protected static void each(PosIterationCallback callback, int[] pos, int dimensions, int dimension, int[] begin, int[] end, int step) {
		for (int i = begin[dimension]; i < end[dimension]; i += step) {
			pos[dimension] = i;

			if (dimension == dimensions-1) {
				callback.call(pos);
			} else {
				each(callback, pos, dimensions, dimension+1, begin, end, step);
			}
		}
	}

	private static int toRGB(int r, int g, int b, int a) {
		int rgb = a;
		rgb = (rgb << 8) + (r & 0xFF);
		rgb = (rgb << 8) + (g & 0xFF);
		rgb = (rgb << 8) + (b & 0xFF);
		return rgb;
	}

	public static void main(String[] args) {
		int[] dims = { 256, 256 };
		System.out.println("Creating.");
//		final MatrixNf content = new SimplexNoiseN(8, new Random(), new FloatInterpolationCubicSpline(), dims).get();
		final Random rand = new Random();
		final FloatInterpolation interpolator = new FloatInterpolationFunc() {
			@Override
			protected float func(float t) {
				return t;
			}
		};
		final MatrixNf content = new SimplexNoiseLayerN(32, rand, interpolator, dims).gen().get();
		System.out.println("Creation finished.");
		System.out.println("Converting to Image.");

		BufferedImage img = new BufferedImage(dims[0]*2, dims[1]*2, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < dims[0]*2; x++) {
			for (int y = 0; y < dims[1]*2; y++) {
				int greyscale = (int)(((content.get(x%dims[0], y%dims[1])+1)*0.5) * 255);
				img.setRGB(x, y, toRGB(greyscale, greyscale, greyscale, 255));
			}
		}

		String directory = "simplex_noise";
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		String filename = directory + "/" + "image";
		String suffix = ".png";

		int i = 0;
		File file;
		do {
			file = new File(filename + i + suffix);
			i++;
		} while (file.exists());
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Writing to file.");
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(file);
			ImageIO.write(img, "png", stream);
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
		System.out.println("Finished.");
	}

}
