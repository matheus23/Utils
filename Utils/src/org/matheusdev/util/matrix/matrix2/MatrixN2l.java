/**
 * 
 */
package org.matheusdev.util.matrix.matrix2;

/**
 * @author matheusdev
 *
 */
public class MatrixN2l extends MappedMatrix2 {

	public long[] values;

	public MatrixN2l(int w, int h) {
		super(w, h);
		values = new long[getSize()];
	}

	public long get(int x, int y) {
		return values[getPosition(x, y)];
	}

	public void set(long val, int x, int y) {
		values[getPosition(x, y)] = val;
	}

}
