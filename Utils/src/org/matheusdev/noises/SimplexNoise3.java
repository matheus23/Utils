package org.matheusdev.noises;

import java.util.Random;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.util.matrix.matrix3.MatrixN3f;

/**
 * @author matheusdev
 *
 */
public class SimplexNoise3 {

	protected static SimplexNoiseLayer3[] layersFromOctaves(
			final int width,
			final int height,
			final int depth,
			final int octaves,
			final int smoothness,
			final int increase,
			final Random rand,
			final FloatInterpolation interpolator) {
		SimplexNoiseLayer3[] layers = new SimplexNoiseLayer3[octaves];
		int v = smoothness;
		for (int i = 0; i < octaves; i++) {
			layers[i] = new SimplexNoiseLayer3(width, height, depth, v, rand, interpolator);
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

	protected final MatrixN3f values;

	public SimplexNoise3(final int width, final int height, final int depth, final int octaves, final Random rand, final FloatInterpolation interpolator) {
		this(width, height, depth, layersFromOctaves(width, height, depth, octaves, 4, 2, rand, interpolator), importancesFromOctaves(octaves, 2), rand);
	}

	public SimplexNoise3(final int width, final int height, final int depth, final SimplexNoiseLayer3[] layers, final float[] weights, final Random rand) {
		if (layers.length != weights.length) throw new IllegalArgumentException("layers.length != weights.length");

		for (int i = 0; i < layers.length; i++) {
			layers[i].gen();
		}

		values = new MatrixN3f(width, height, depth);

		for (int i = 0; i < layers.length; i++) {
			final int xoffset = genOffset(width, rand);
			final int yoffset = genOffset(height, rand);
			final int zoffset = genOffset(depth, rand);

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					for (int z = 0; z < depth; z++) {
						values.set(
								values.get(x, y, z) + (layers[i].get().get((x + xoffset) % width, (y + yoffset) % height, (z + zoffset) % depth) * weights[i]),
								x, y, z);
					}
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

	public MatrixN3f get() {
		return values;
	}

}
