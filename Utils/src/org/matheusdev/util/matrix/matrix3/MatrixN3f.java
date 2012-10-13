package org.matheusdev.util.matrix.matrix3;

/**
 * @author matheusdev
 *
 */
public class MatrixN3f extends MappedMatrix3 {

	public float[] values;

	public MatrixN3f(int w, int h, int d) {
		super(w, h, d);
		values = new float[getSize()];
	}

	public float get(int x, int y, int z) {
		return values[getPosition(x, y, z)];
	}

	public void set(float val, int x, int y, int z) {
		values[getPosition(x, y, z)] = val;
	}

}
