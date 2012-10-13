package org.matheusdev.util.matrix.matrix3;

/**
 * @author matheusdev
 *
 */
public class MatrixN3i extends MappedMatrix3 {

	public int[] values;

	public MatrixN3i(int w, int h, int d) {
		super(w, h, d);
		values = new int[getSize()];
	}

	public int get(int x, int y, int z) {
		return values[getPosition(x, y, z)];
	}

	public void set(int val, int x, int y, int z) {
		values[getPosition(x, y, z)] = val;
	}

}
