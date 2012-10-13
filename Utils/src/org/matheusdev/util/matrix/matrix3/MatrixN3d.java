package org.matheusdev.util.matrix.matrix3;

/**
 * @author matheusdev
 *
 */
public class MatrixN3d extends MappedMatrix3 {

	public double[] values;

	public MatrixN3d(int w, int h, int d) {
		super(w, h, d);
		values = new double[getSize()];
	}

	public double get(int x, int y, int z) {
		return values[getPosition(x, y, z)];
	}

	public void set(double val, int x, int y, int z) {
		values[getPosition(x, y, z)] = val;
	}

}
