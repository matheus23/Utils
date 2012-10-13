package org.matheusdev.util.matrix.matrix3;

/**
 * @author matheusdev
 *
 */
public class MatrixN3bl extends MappedMatrix3 {

	public boolean[] values;

	public MatrixN3bl(int w, int h, int d) {
		super(w, h, d);
		values = new boolean[getSize()];
	}

	public boolean get(int x, int y, int z) {
		return values[getPosition(x, y, z)];
	}

	public void set(boolean val, int x, int y, int z) {
		values[getPosition(x, y, z)] = val;
	}

}
