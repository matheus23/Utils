package org.matheusdev;

import org.matheusdev.util.FastRand;
import org.matheusdev.util.matrix.MatrixNbl;



/**
 * @author matheusdev
 *
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int w = 20;
		final int h = 20;
		final FastRand rand = new FastRand();

		MatrixNbl mat = new MatrixNbl(w, h);

		System.out.println("Precent elements: " + mat.values.length);
		System.out.println("Actually needed elements: " + ((w * h) / 8));
		System.out.println("w * h: " + (w * h));

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				boolean bool = rand.randBool();
				mat.set(bool, x, y);
				System.out.print(bool ? "#" : ".");
			}
			System.out.println();
		}
		System.out.println();

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				System.out.print(mat.get(x, y) ? "#" : ".");
			}
			System.out.println();
		}
	}

}
