package org.matheusdev.noises;

import java.util.Random;

import org.matheusdev.interpolation.FloatInterpolation;

/**
 * @author matheusdev
 *
 */
public class SimplexNoise {

	protected final float[] values;

	protected static SimplexNoiseLayer[] layersFromOctaves(final int length, final int octaves, final int smoothness, final int increase, final Random rand, final FloatInterpolation interpolator) {
		SimplexNoiseLayer[] layers = new SimplexNoiseLayer[octaves];
		int v = smoothness;
		for (int i = 0; i < octaves; i++) {
			layers[i] = new SimplexNoiseLayer(length, v, rand, interpolator);
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

	public SimplexNoise(final int length, final int octaves, final Random rand, final FloatInterpolation interpolator) {
		this(length, layersFromOctaves(length, octaves, 2, 2, rand, interpolator), importancesFromOctaves(octaves, 2));
	}

	public SimplexNoise(final int length, final int octaves, final int smoothness, final int smoothnessincrease, final int importanceincrease, final Random rand, final FloatInterpolation interpolator) {
		this(length, layersFromOctaves(length, octaves, smoothness, smoothnessincrease, rand, interpolator), importancesFromOctaves(octaves, importanceincrease));
	}

	public SimplexNoise(int length, SimplexNoiseLayer[] layers, float[] importances) {
		values = new float[length];

		for (int i = 1; i < layers.length; i++) {
			final float importance = importances[i];
			final float[] vals = layers[i].get();

			for (int j = 0; j < vals.length; j++) {
				values[j] += vals[j] * importance;
			}
		}

		float totalImportance = 0;
		for (float f : importances) {
			totalImportance += f;
		}

		for (int i = 0; i < values.length; i++) {
			values[i] /= totalImportance;
		}
	}

	public float[] get() {
		return values;
	}

}
