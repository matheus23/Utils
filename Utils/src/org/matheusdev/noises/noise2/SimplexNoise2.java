package org.matheusdev.noises.noise2;

import java.util.Random;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.util.matrix.matrix2.MatrixN2f;

/**
 * <p>Computes 2-Dimensional Simplex noise.</p>
 * <p>One can use this to generate Minecraft-like
 * terrain, for example.</p>
 * @author matheusdev
 *
 */
public class SimplexNoise2 implements Noise2 {

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

	/**
	 * @param width the width of the noise image to create.
	 * @param height the height of the noise image to create.
	 * @param octaves the number of layers of simplex noise layers to combine. see {@link org.matheusdev.noises.noise2.SimplexNoiseLayer2}
	 * @param rand a {@link java.util.Random} instance for generating random numbers.
	 * @param interpolator a {@link org.matheusdev.interpolation.FloatInterpolation} instance for interpolating numbers.
	 * This parameter affects the output of this system. FloatInterpolationCubigSpline is recommended.
	 */
	public SimplexNoise2(final int width, final int height, final int octaves, final Random rand, final FloatInterpolation interpolator) {
		this(width, height, layersFromOctaves(width, height, octaves, 4, 2, rand, interpolator), importancesFromOctaves(octaves, 2), rand);
	}

	/**
	 * <p>Creates the Simplex noise with the given layers and it's weights.</p>
	 * @param width the width of the noise image to create.
	 * @param height the height of the noise image to create.
	 * @param layers the layers of generated simplex-noise-layers to combine.
	 * @param weights the weights of each layer.
	 * @param rand a {@link java.util.Random} instance for generating random numbers.
	 */
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

		for (int i = 0; i < values.values.length; i++) {
			values.values[i] /= sum;
		}
	}

	protected int genOffset(final int max, final Random rand) {
		int offset = Math.abs(rand.nextInt() % max);
		if (offset % 2 == 0) offset++;
		return offset;
	}

	/**
	 * @return the generated values as {@link org.matheusdev.util.matrix.matrix2.MatrixN2f}.
	 */
	public MatrixN2f get() {
		return values;
	}

    public float get(float x, float y) {
        return values.get((int) x, (int) y);
    }

}
