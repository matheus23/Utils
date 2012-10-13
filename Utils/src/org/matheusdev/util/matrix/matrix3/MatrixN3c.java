package org.matheusdev.util.matrix.matrix3;

/**
 * @author matheusdev
 *
 */
public class MatrixN3c extends MappedMatrix3 {

	public char[] values;

	public MatrixN3c(int w, int h, int d) {
		super(w, h, d);
		values = new char[getSize()];
	}

	public char get(int x, int y, int z) {
		return values[getPosition(x, y, z)];
	}

	public void set(char val, int x, int y, int z) {
		values[getPosition(x, y, z)] = val;
	}

}
