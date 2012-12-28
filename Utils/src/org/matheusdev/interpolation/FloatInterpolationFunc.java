package org.matheusdev.interpolation;


/**
 * <p>This class automatically scales the given
 * function to the values needed in
 * {@link #interpolate(float, float, float, float, float)}.</p>
 * <p>This class is to be sub-classed. The function
 * returned from {@link #func(float)} is supposed to
 * go through the point (0,0) and (1,1).</p>
 *
 * @author matheusdev
 *
 */
public abstract class FloatInterpolationFunc extends FloatInterpolation {

	/* (non-Javadoc)
	 * @see org.worldOfCube.client.math.interpolation.FloatInterpolation#interpolate(float, float, float, float, float)
	 */
	@Override
	public final float interpolate(float t, float y0, float x0, float y1, float x1) {
		return (func(t)) * (y1 - y0) + y0;
	}

	/**
	 * @param t x-axis position.
	 * @return the according y-axis value.
	 */
	protected abstract float func(float t);

}
