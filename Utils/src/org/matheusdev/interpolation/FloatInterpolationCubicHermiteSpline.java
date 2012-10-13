package org.matheusdev.interpolation;


/**
 * @author matheusdev
 * @deprecated Use <tt>FloatInterpolationCubicSpline</tt> instead.
 */
@Deprecated()
public class FloatInterpolationCubicHermiteSpline extends FloatInterpolation {

	/* (non-Javadoc)
	 * @see org.worldOfCube.client.math.interpolation.FloatInterpolation#interpolate(float, float, float, float)
	 */
	@Override
	public float interpolate(float t, float y0, float x0, float y1, float x1) {
		float t2 = t*t;
		float t3 = t2*t;
		return (2*t3 - 3*t2 + 1)*0 + (t3-2*t2+t)*y0 + (-2*t3+3*t2)*1 + (t3-t2)*y1;
	}

}
