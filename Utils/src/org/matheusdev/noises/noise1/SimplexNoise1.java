package org.matheusdev.noises.noise1;

import java.util.Random;

import org.matheusdev.interpolation.FloatInterpolation;

/**
 * @author matheusdev
 *
 */
public class SimplexNoise1 {

	protected static SimplexNoiseLayer1[] layersFromOctaves(
			final int length,
			final int octaves,
			final int smoothness,
			final int increase,
			final Random rand,
			final FloatInterpolation interpolator) {
		SimplexNoiseLayer1[] layers = new SimplexNoiseLayer1[octaves];
		int v = smoothness;
		for (int i = 0; i < octaves; i++) {
			layers[i] = new SimplexNoiseLayer1(length, v, rand, interpolator);
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

	protected final float[] values;

	public SimplexNoise1(final int length, final int octaves, final Random rand, final FloatInterpolation interpolator) {
		this(length, layersFromOctaves(length, octaves, 4, 2, rand, interpolator), importancesFromOctaves(octaves, 2), rand);
	}

	public SimplexNoise1(final int length, final SimplexNoiseLayer1[] layers, final float[] weights, final Random rand) {
		if (layers.length != weights.length) throw new IllegalArgumentException("layers.length != weights.length");

		for (int i = 0; i < layers.length; i++) {
			layers[i].gen();
		}

		values = new float[length];

		for (int layer = 0; layer < layers.length; layer++) {
			final int offset = genOffset(values.length, rand);

			for (int i = 0; i < values.length; i++) {
					values[i] += (layers[layer].get()[(i + offset) % values.length]) * weights[i];
			}
		}

		float sum = 0;
		for (int i = 0; i < weights.length; i++) {
			sum += weights[i];
		}

		for (int i = 0; i < values.length; i++) {
			values[i] /= sum;
		}
	}

	protected int genOffset(final int max, final Random rand) {
		int offset = Math.abs(rand.nextInt() % max);
		if (offset % 2 == 0) offset++;
		return offset;
	}

	public float[] get() {
		return values;
	}

}
