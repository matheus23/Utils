package org.matheusdev.noises;

import java.util.Random;

import org.matheusdev.interpolation.FloatInterpolation;

/**
 * @author matheusdev
 *
 */
public class SimplexNoiseLayer {

	protected final float[] values;
	protected final int density;
	protected final Random rand;
	protected final FloatInterpolation interpolator;

	public SimplexNoiseLayer(final int length, final int density, final Random rand, final FloatInterpolation interpolator) {
		values = new float[length];
		this.density = Math.max(0, Math.min(length-1, density));
		this.rand = rand;
		this.interpolator = interpolator;
		generate();
	}

	protected void generate() {
		// Random generation step:
		for (int i = 0; i < values.length; i += density) {
			values[i] = rand.nextBoolean() ? 1f : -1f;
		}
		// Interpolation step:
		for (int i = 0; i < values.length; i += density) {
			final float val1 = values[i];
			final float val2 = values[(i + density) % values.length];

			for (int j = i+1; j < (i + density); j++) {
				final float left = diff(i, j);
				final float right = diff(i+density, j);

				values[j] = interpolator.interpolate(left/density, val1, right, val2, left);
			}
		}
	}

	private float diff(float val1, float val2) {
		return Math.abs(val1-val2);
	}

	public float[] get() {
		return values;
	}

}
