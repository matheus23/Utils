package org.matheusdev.demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.interpolation.FloatInterpolationFunc;
import org.matheusdev.noises.noise2.SimplexNoise2;
import org.matheusdev.util.matrix.matrix2.MatrixN2f;

/**
 * @author matheusdev
 *
 */
public final class SimplexNoise2PngGenerator {

	private SimplexNoise2PngGenerator() {}

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

        SimplexNoise2 noise = new SimplexNoise2(width, height, 6, rand, interp);

        Noise2PngGenerator.gen(noise, width, height);
	}

}
