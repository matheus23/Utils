package org.matheusdev.demo;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.interpolation.FloatInterpolationFunc;
import org.matheusdev.noises.noise2.SimplexNoiseLazy2;
import org.matheusdev.noises.noise3.SimplexNoiseLazy3;
import org.matheusdev.util.FastRand;

/**
 * @author matheusdev
 *
 */
public final class SimplexNoiseLazy3GifGenerator {

	private SimplexNoiseLazy3GifGenerator() {}

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

		SimplexNoiseLazy3 noise = new SimplexNoiseLazy3(new FastRand().randLong(), 6, 2, interp);

        Noise3GifGenerator.gen(noise, width, height, 64);
	}

}
