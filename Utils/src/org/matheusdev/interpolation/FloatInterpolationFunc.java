package org.matheusdev.interpolation;


/**
 * @author matheusdev
 *
 */
public abstract class FloatInterpolationFunc extends FloatInterpolation {

	/* (non-Javadoc)
	 * @see org.worldOfCube.client.math.interpolation.FloatInterpolation#interpolate(float, float, float, float, float)
	 */
	@Override
	public final float interpolate(float t, float y0, float x0, float y1, float x1) {
		// 3 * t^2 - 2 * t^3 [only scaling]
		return (func(t)) * (y1 - y0) + y0;
	}

	protected abstract float func(float t);

}
