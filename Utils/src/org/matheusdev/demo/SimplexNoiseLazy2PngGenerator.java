package org.matheusdev.demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.interpolation.FloatInterpolationFunc;
import org.matheusdev.noises.noise2.SimplexNoiseLayerLazy2;
import org.matheusdev.noises.noise2.SimplexNoiseLazy2;
import org.matheusdev.util.FastRand;
import org.matheusdev.util.vecs.Vec2l;

/**
 * @author matheusdev
 *
 */
public final class SimplexNoiseLazy2PngGenerator {

	private SimplexNoiseLazy2PngGenerator() {}

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

		SimplexNoiseLazy2 noise = new SimplexNoiseLazy2(new FastRand().randLong(), 6, 4, interp);

        Noise2PngGenerator.gen(noise, width, height);
	}

}
