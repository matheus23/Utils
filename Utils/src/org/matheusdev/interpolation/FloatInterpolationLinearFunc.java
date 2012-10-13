package org.matheusdev.interpolation;


/**
 * @author matheusdev
 *
 */
public class FloatInterpolationLinearFunc extends FloatInterpolation {

	/* (non-Javadoc)
	 * @see org.worldOfCube.client.math.interpolation.FloatInterpolation#interpolate(float, float, float, float)
	 */
	@Override
	public float interpolate(float t, float y0, float x0, float y1, float x1) {
		return (t * y0 + (1-t) * y1);
	}

}
