package org.matheusdev.interpolation;

/**
 * @author matheusdev
 *
 */
public abstract class FloatInterpolation {

	/**
	 * <p>This is the function the subclasses are supposed to
	 * override.</p>
	 * <p>Subclasses should return the interpolated values
	 * according to a given interpolation function.</p>
	 * <p>For existing classes see @see's.</p>
	 * <p>You can also use
	 * {@link org.matheusdev.interpolation.FloatInterpolationFunc},
	 * which is a helper class allowing you to define any function
	 * which is automatically scaled and changed so it fits into
	 * the given {@link #interpolate(float, float, float, float, float)}
	 * inputs.</p>
	 * @param t the time. Should be between 0 and 1.
	 * @param y0 the first value. (edge value of interpolation)
	 * @param x0 the first value's position. If not known, use 0.
	 * @param y1 the second value. (edge value of interpolation)
	 * @param x1 the second value's position. If not known, use 1.
	 * @return the interpolated value.
	 * @see FloatInterpolationCubicSpline
	 * @see FloatInterpolationLinear
	 * @see FloatInterpolationLinearFunc
	 */
	public abstract float interpolate(float t, float y0, float x0, float y1, float x1);

	/**
	 * <p>Returns the difference between two numbers.</p>
	 * <pre>
	 * return Math.abs(f1-f2);
	 * </pre>
	 * @param f0 value0
	 * @param f1 value1
	 * @return the difference between f1 and f2.
	 */
	public float diff(float f0, float f1) {
		return Math.abs(f0-f1);
	}

}
