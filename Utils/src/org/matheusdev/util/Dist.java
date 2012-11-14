package org.matheusdev.util;

/**
 * @author matheusdev
 *
 */
public final class Dist {

	private Dist() {
	}

	public static double lin(double x0, double y0, double x1, double y1) {
		return Math.abs(x0 - x1) + Math.abs(y0 - y1);
	}

	public static double rect(double x0, double y0, double x1, double y1) {
		return Math.max(Math.abs(x0-x1), Math.abs(y0-y1));
	}

	public static int rect(int x0, int y0, int x1, int y1) {
		return Math.max(Math.abs(x0-x1), Math.abs(y0-y1));
	}

	public static double root(double x0, double y0, double x1, double y1) {
		double dx = Math.abs(x0-x1);
		double dy = Math.abs(y0-y1);
		return Math.sqrt(dx*dx + dy*dy);
	}

	public static float root(float x0, float y0, float x1, float y1) {
		float dx = Math.abs(x0-x1);
		float dy = Math.abs(y0-y1);
		return (float) Math.sqrt(dx*dx + dy*dy);
	}

	public static double squared(double x0, double y0, double x1, double y1) {
		double dx = Math.abs(x0-x1);
		double dy = Math.abs(y0-y1);
		return dx*dx + dy*dy;
	}

}
