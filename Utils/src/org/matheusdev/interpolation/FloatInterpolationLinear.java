package org.matheusdev.interpolation;


/**
 * @author matheusdev
 *
 */
public class FloatInterpolationLinear extends FloatInterpolation {

	public static final FloatInterpolationLinear inst = new FloatInterpolationLinear();

	/* (non-Javadoc)
	 * @see org.worldOfCube.client.math.interpolation.FloatInterpolation#interpolate(float, float, float, float)
	 */
	@Override
	public float interpolate(float t, float y0, float x0, float y1, float x1) {
		return (y0 * x0 + y1 * x1) / (x0 + x1);
	}

}
