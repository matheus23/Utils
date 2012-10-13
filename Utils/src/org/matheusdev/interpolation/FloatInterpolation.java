package org.matheusdev.interpolation;

/**
 * @author matheusdev
 *
 */
public abstract class FloatInterpolation {

	public abstract float interpolate(float t, float y0, float x0, float y1, float x1);

	public float diff(float f1, float f2) {
		return Math.abs(f1-f2);
	}

}
