package org.matheusdev.util.matrix.matrix3;

/**
 * @author matheusdev
 *
 */
public class MatrixN3b extends MappedMatrix3 {

	public byte[] values;

	public MatrixN3b(int w, int h, int d) {
		super(w, h, d);
		values = new byte[getSize()];
	}

	public byte get(int x, int y, int z) {
		return values[getPosition(x, y, z)];
	}

	public void set(byte val, int x, int y, int z) {
		values[getPosition(x, y, z)] = val;
	}

}
