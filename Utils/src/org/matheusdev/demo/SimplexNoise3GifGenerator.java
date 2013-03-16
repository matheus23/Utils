package org.matheusdev.demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.interpolation.FloatInterpolationFunc;
import org.matheusdev.noises.noise3.SimplexNoise3;
import org.matheusdev.util.GifSequenceWriter;
import org.matheusdev.util.matrix.matrix3.MatrixN3f;

/**
 * @author matheusdev
 *
 */
public final class SimplexNoise3GifGenerator {

	private SimplexNoise3GifGenerator() {}

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

		SimplexNoise3 noise = new SimplexNoise3(width, height, depth, 5, rand, interp);

        Noise3GifGenerator.gen(noise, width, height, depth);
	}

}
