/**
 * 
 */
package org.matheusdev.util.matrix.matrix2;

/**
 * @author matheusdev
 *
 */
public class MatrixN2i extends MappedMatrix2 {

	public int[] values;

	public MatrixN2i(int w, int h) {
		super(w, h);
		values = new int[getSize()];
	}

	public int get(int x, int y) {
		return values[getPosition(x, y)];
	}

	public void set(int val, int x, int y) {
		values[getPosition(x, y)] = val;
	}

}
