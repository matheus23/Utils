package org.matheusdev.interpolation;


/**
 * <p>To use this class you would usually use the
 * instance {@link #inst} of this class.</p>
 * <p>Uses the function 3 * t^2 - 2 * t^3.</p>
 *
 * @author matheusdev
 *
 */
public class FloatInterpolationCubicSpline extends FloatInterpolation {

	public static final FloatInterpolationCubicSpline inst = new FloatInterpolationCubicSpline();

	/**
	 * <p>Implements the function
	 * <code> 3 * t^2 - 2 * t^3</code>.
	 * </p>
	 */
	/* (non-Javadoc)
	 * @see org.worldOfCube.client.math.interpolation.FloatInterpolation#interpolate(float, float, float, float, float)
	 */
	@Override
	public float interpolate(float t, float y0, float x0, float y1, float x1) {
		// 3 * t^2 - 2 * t^3 [only scaling]
		float t2 = t*t;
		return (3*t2 - 2*t2*t) * (y1 - y0) + y0;
	}

}
