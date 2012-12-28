package org.matheusdev.noises.noise1;

import java.util.Random;

import org.matheusdev.interpolation.FloatInterpolation;

/**
 * <p>Computes 1-Dimensional Simplex noise.</p>
 * <p>One can use this to generate Terraria-like
 * 2D surfaces, for example.</p>
 *
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

	/**
	 * @param length the length of the array the noise should generate.
	 * @param octaves the number of octaves (layers) see {@link org.matheusdev.noises.noise1.SimplexNoiseLayer1}.
	 * @param rand a {@link java.util.Random} instance for generating random numbers.
	 * @param interpolator a {@link org.matheusdev.interpolation.FloatInterpolation} instance for interpolating numbers.
	 * This parameter affects the output of this system. FloatInterpolationCubigSpline is recommended.
	 */
	public SimplexNoise1(final int length, final int octaves, final Random rand, final FloatInterpolation interpolator) {
		this(length, layersFromOctaves(length, octaves, 4, 2, rand, interpolator), importancesFromOctaves(octaves, 2), rand);
	}

	/**
	 * <p>Creates the Simplex noise with the given layers and it's weights.</p>
	 * @param length the length of the array the noise should generate.
	 * @param layers the layers of generated simplex-noise-layers to combine.
	 * @param weights the weights of each layer.
	 * @param rand a {@link java.util.Random} instance for generating random numbers
	 */
	public SimplexNoise1(final int length, final SimplexNoiseLayer1[] layers, final float[] weights, final Random rand) {
		if (layers.length != weights.length) throw new IllegalArgumentException("layers.length != weights.length");

		for (int i = 0; i < layers.length; i++) {
			layers[i].gen();
		}

		values = new float[length];

		for (int layer = 0; layer < layers.length; layer++) {
			final int offset = genOffset(values.length, rand);

			for (int i = 0; i < values.length; i++) {
					values[i] += (layers[layer].get()[(i + offset) % values.length]) * weights[layer];
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

	/**
	 * @return the generated values.
	 */
	public float[] get() {
		return values;
	}

}
