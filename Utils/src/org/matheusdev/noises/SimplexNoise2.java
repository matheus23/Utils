package org.matheusdev.noises;

import java.util.Random;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.util.matrix.matrix2.MatrixN2f;

/**
 * @author matheusdev
 *
 */
public class SimplexNoise2 {

	protected static SimplexNoiseLayer2[] layersFromOctaves(
			final int width,
			final int height,
			final int octaves,
			final int smoothness,
			final int increase,
			final Random rand,
			final FloatInterpolation interpolator) {
		SimplexNoiseLayer2[] layers = new SimplexNoiseLayer2[octaves];
		int v = smoothness;
		for (int i = 0; i < octaves; i++) {
			layers[i] = new SimplexNoiseLayer2(width, height, v, rand, interpolator);
			v *= increase;
		}
		return layers;
	}

	protected static float[] importancesFromOctaves(final int octaves, final int increase) {
		float[] importances = new float[octaves];
		float v = 1;
		for (int i = 0; i < octaves; i++) {
			importances[i] = v;
			v *= increase;
		}
		return importances;
	}

	protected final MatrixN2f values;

	public SimplexNoise2(final int width, final int height, final int octaves, final Random rand, final FloatInterpolation interpolator) {
		this(width, height, layersFromOctaves(width, height, octaves, 4, 2, rand, interpolator), importancesFromOctaves(octaves, 2), rand);
	}

	public SimplexNoise2(final int width, final int height, final SimplexNoiseLayer2[] layers, final float[] weights, final Random rand) {
		if (layers.length != weights.length) throw new IllegalArgumentException("layers.length != weights.length");

		for (int i = 0; i < layers.length; i++) {
			layers[i].gen();
		}

		values = new MatrixN2f(width, height);

		for (int i = 0; i < layers.length; i++) {
			final int xoffset = genOffset(width, rand);
			final int yoffset = genOffset(height, rand);

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					values.set(
							values.get(x, y) + (layers[i].get().get((x + xoffset) % width, (y + yoffset) % height) * weights[i]),
							x, y);
				}
			}
		}

		float sum = 0;
		for (int i = 0; i < weights.length; i++) {
			sum += weights[i];
		}
		System.out.println(sum);

		for (int i = 0; i < values.values.length; i++) {
			values.values[i] /= sum;
		}
	}

	protected int genOffset(final int max, final Random rand) {
		int offset = Math.abs(rand.nextInt() % max);
		if (offset % 2 == 0) offset++;
		return offset;
	}

	public MatrixN2f get() {
		return values;
	}

}
