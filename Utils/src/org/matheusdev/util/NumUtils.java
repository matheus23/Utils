package org.matheusdev.util;

/**
 * @author matheusdev
 *
 */
public final class NumUtils {

	private NumUtils() {}

	public static boolean outside(int i, int min, int max) {
		return (i < min || i > max);
	}

	public static float diff(float val1, float val2) {
		return Math.abs(val1-val2);
	}

	public static int clamp(int i, int height) {
		if (height <= 0) throw new IllegalArgumentException("height <= 0");
		if (i == 0) return 0;
		if (i < 0) return height + (i % height);
		else return i % height;
	}

}
