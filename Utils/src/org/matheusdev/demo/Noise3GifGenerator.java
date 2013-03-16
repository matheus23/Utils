package org.matheusdev.demo;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.interpolation.FloatInterpolationFunc;
import org.matheusdev.noises.noise3.Noise3;
import org.matheusdev.noises.noise3.SimplexNoise3;
import org.matheusdev.util.GifSequenceWriter;
import org.matheusdev.util.matrix.matrix3.MatrixN3f;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @author matheusdev
 *
 */
public final class Noise3GifGenerator {

	private Noise3GifGenerator() {}

	protected static int toRGB(int r, int g, int b, int a) {
		int rgb = a;
		rgb = (rgb << 8) + (r & 0xFF);
		rgb = (rgb << 8) + (g & 0xFF);
		rgb = (rgb << 8) + (b & 0xFF);
		return rgb;
	}

	public static void gen(Noise3 noise, int width, int height, int depth) {
		System.out.println("Converting.");
		BufferedImage[] imgs = new BufferedImage[depth];

		for (int layer = 0; layer < depth; layer++) {
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					float val = (noise.get(x, y, layer)+1)/2;
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
