package org.matheusdev;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author matheusdev
 *
 */
public class GriddedImage {

	public static void create(int width, int height, int cellWidth, int cellHeight, int innerColor, int outerColor, String filename) {
		(new GriddedImage(width, height, cellWidth, cellHeight, innerColor, outerColor)).writeToFile(filename);
	}

	protected BufferedImage img;

	protected GriddedImage(int w, int h, int cw, int ch, int col1, int col2) {
		img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				boolean isLine = false;
				if (x % cw == cw-1) isLine = true;
				if (y % ch == ch-1) isLine = true;

				img.setRGB(x, y, isLine ? col2 : col1);
			}
		}
	}

	public void writeToFile(String filename) {
		File file = new File(filename);
		try {
			file.createNewFile();
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		boolean verbose = false;

		int w = 512;
		int h = 512;

		int cw = 32;
		int ch = 32;

		int ic = 0xFFFF00FF;
		int oc = 0xFF000000;

		String filename = "raster.png";

		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("--verbose")) {
				verbose = true;
			} else if (args[i].toLowerCase().startsWith("-w")) {
				w = Integer.parseInt(args[i].substring(2));
			} else if (args[i].toLowerCase().startsWith("-h")) {
				h = Integer.parseInt(args[i].substring(2));
			} else if (args[i].toLowerCase().startsWith("-cw")) {
				cw = Integer.parseInt(args[i].substring(3));
			} else if (args[i].toLowerCase().startsWith("-ch")) {
				ch = Integer.parseInt(args[i].substring(3));
			} else if (args[i].toLowerCase().startsWith("-ic")) {
				ic = Integer.parseInt(args[i].substring(3));
			} else if (args[i].toLowerCase().startsWith("-oc")) {
				oc = Integer.parseInt(args[i].substring(3));
			}
			else {
				filename = args[i];
			}
		}

		if (verbose) System.out.println("Parsed arguments. Starting.");
		create(w, h, cw, ch, ic, oc, filename);
		if (verbose) System.out.println("Finished. Exiting.");
	}

}
