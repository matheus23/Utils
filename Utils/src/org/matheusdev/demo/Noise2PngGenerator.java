package org.matheusdev.demo;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.interpolation.FloatInterpolationFunc;
import org.matheusdev.noises.noise2.Noise2;
import org.matheusdev.noises.noise2.SimplexNoise2;
import org.matheusdev.util.matrix.matrix2.MatrixN2f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Author: matheusdev
 * Date: 3/16/13
 * Time: 6:31 PM
 */
public class Noise2PngGenerator {

    protected static int toRGB(int r, int g, int b, int a) {
        int rgb = a;
        rgb = (rgb << 8) + (r & 0xFF);
        rgb = (rgb << 8) + (g & 0xFF);
        rgb = (rgb << 8) + (b & 0xFF);
        return rgb;
    }

    public static void gen(Noise2 noise, int width, int height) {
        System.out.println("Generating.");
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //img.setRGB(x, y, ((int)(((values.get(x, y)+1)/2)*10000)) | 0xFF000000);
                float val = (noise.get(x, y)+1)/2;
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
