package org.matheusdev.util.matrix.matrix3;

/**
 * @author matheusdev
 *
 */
public class MatrixN3l extends MappedMatrix3 {

	public long[] values;

	public MatrixN3l(int w, int h, int d) {
		super(w, h, d);
		values = new long[getSize()];
	}

	public long get(int x, int y, int z) {
		return values[getPosition(x, y, z)];
	}

	public void set(long val, int x, int y, int z) {
		values[getPosition(x, y, z)] = val;
	}

}
