package org.matheusdev.util;

/**
 * @author matheusdev
 *
 */
public final class ConsoleFloatVisualizer {

	private ConsoleFloatVisualizer() {
	}

	public static void viz(float[] array, float add, float multiply) {
		for (int i = 0; i < array.length; i++) {
			System.out.printf("%+G ", array[i]);
			float bar = (array[i] + add) * multiply;

			for (int j = 0; j < bar; j++) {
				System.out.print("#");
			}
			System.out.println();
		}
	}

}
