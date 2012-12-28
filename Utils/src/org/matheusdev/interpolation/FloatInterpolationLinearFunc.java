package org.matheusdev.interpolation;


/**
 * <p>This class interpolates the given values linearly.</p>
 * <p>Opposing to
 * {@link org.matheusdev.interpolation.FloatInterpolationLinear}
 * this class does not use the x0 and x1 parameters and is there
 * fore faster.</p>
 *
 * @author matheusdev
 *
 */
public class FloatInterpolationLinearFunc extends FloatInterpolation {

	public static final FloatInterpolationLinearFunc inst = new FloatInterpolationLinearFunc();

	/* (non-Javadoc)
	 * @see org.worldOfCube.client.math.interpolation.FloatInterpolation#interpolate(float, float, float, float)
	 */
	@Override
	public float interpolate(float t, float y0, float x0, float y1, float x1) {
		return (t * y0 + (1-t) * y1);
	}

}
