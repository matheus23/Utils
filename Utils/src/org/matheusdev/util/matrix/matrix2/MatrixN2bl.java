/**
 * 
 */
package org.matheusdev.util.matrix.matrix2;

/**
 * @author matheusdev
 *
 */
public class MatrixN2bl extends MappedMatrix2 {

	public boolean[] values;

	public MatrixN2bl(int w, int h) {
		super(w, h);
		values = new boolean[getSize()];
	}

	public boolean get(int x, int y) {
		return values[getPosition(x, y)];
	}

	public void set(boolean val, int x, int y) {
		values[getPosition(x, y)] = val;
	}

}
