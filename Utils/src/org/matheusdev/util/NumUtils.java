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

}
