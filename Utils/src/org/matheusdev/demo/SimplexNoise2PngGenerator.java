package org.matheusdev.demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.interpolation.FloatInterpolationFunc;
import org.matheusdev.noises.SimplexNoise2;
import org.matheusdev.util.matrix.matrix2.MatrixN2f;

/**
 * @author matheusdev
 *
 */
public final class SimplexNoise2PngGenerator {

	private SimplexNoise2PngGenerator() {}

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

		final FloatInterpolation interp = new FloatInterpolationFunc() {
			@Override
			protected float func(float t) {
				float t2 = t * t;
				return 3 * t2 - 2 * t2 * t;
			}
		};
		final Random rand = new Random();

		System.out.println("Generating.");
		MatrixN2f values = new SimplexNoise2(width, height, 6, rand, interp).get();

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				//img.setRGB(x, y, ((int)(((values.get(x, y)+1)/2)*10000)) | 0xFF000000);
				float val = (values.get(x, y)+1)/2;
				int greyscale = (int)(val * 255);
				img.setRGB(x, y, toRGB(greyscale, greyscale, greyscale, 255));
			}
		}

		System.out.println("Writing to file.");
		String directory = "simplex_noise_2d";
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		String filename = directory + "/" + "noise";
		String suffix = ".png";

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
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(file);
			ImageIO.write(img, "png", stream);
			stream.close();
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
		System.out.println("Finished. Written to file " + (filename + i + suffix));
	}

}
