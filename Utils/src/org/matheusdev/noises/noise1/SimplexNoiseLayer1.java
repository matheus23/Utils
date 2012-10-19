package org.matheusdev.noises.noise1;

import java.util.Random;

import org.matheusdev.interpolation.FloatInterpolation;

/**
 * @author matheusdev
 *
 */
public class SimplexNoiseLayer1 {

	protected final float[] values;
	protected final int density;
	protected final Random rand;
	protected final FloatInterpolation interpolator;

	public SimplexNoiseLayer1(int length, int density, final Random rand, final FloatInterpolation interpolator) {
		this.values = new float[length];
		this.density = density;
		this.rand = rand;
		this.interpolator = interpolator;
	}

	public SimplexNoiseLayer1 gen() {
		for (int i = 0; i < values.length; i += density) {
			values[i] = rand.nextBoolean() ? -1f : 1f;
		}

		int i0;
		int i1;

		for (int celli = 0; celli < values.length/density; celli++) {
			i0 = celli * density;
			i1 = i0 + density;

			for (int i = i0; i < i1; i++) {
				// If it's not the top-left edge of the cell
				if (i != i0) {
					float t = (float)(i % density) / (float)density;

					float edge0 = values[i0 % values.length];
					float edge1 = values[i1 % values.length];

					values[i] = interpolator.interpolate(t, edge0, 0, edge1, 1);
				}
			}
		}
		return this;
	}

	public float[] get() {
		return values;
	}

}
