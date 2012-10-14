package org.matheusdev.demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.interpolation.FloatInterpolationFunc;
import org.matheusdev.noises.SimplexNoise3;
import org.matheusdev.util.GifSequenceWriter;
import org.matheusdev.util.matrix.matrix3.MatrixN3f;

/**
 * @author matheusdev
 *
 */
public final class SimplexNoise3GifGenerator {

	private SimplexNoise3GifGenerator() {}

	protected static int toRGB(int r, int g, int b, int a) {
		int rgb = a;
		rgb = (rgb << 8) + (r & 0xFF);
		rgb = (rgb << 8) + (g & 0xFF);
		rgb = (rgb << 8) + (b & 0xFF);
		return rgb;
	}

	public static void main(String[] args) {
		final int width = 512;
		final int height = 512;
		final int depth = 64;

		final Random rand = new Random();
		final FloatInterpolation interp = new FloatInterpolationFunc() {
			@Override
			protected float func(float t) {
				float t2 = t*t;
				return 3 * t2 - 2 * t2 * t;
			}
		};

		System.out.println("Generating.");
		MatrixN3f values = new SimplexNoise3(width, height, depth, 5, rand, interp).get();

		System.out.println("Converting.");
		BufferedImage[] imgs = new BufferedImage[depth];

		for (int layer = 0; layer < depth; layer++) {
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					float val = (values.get(x, y, layer)+1)/2;
					int greyscale = (int)(val * 255);
					img.setRGB(x, y, toRGB(greyscale, greyscale, greyscale, 255));
				}
			}
			imgs[layer] = img;
		}

		String directory = "simplex_noise_3d";
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		String filename = directory + "/" + "noise";
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
		System.out.println("Finished writing (to " + filename + i + suffix + ").");
	}

}
