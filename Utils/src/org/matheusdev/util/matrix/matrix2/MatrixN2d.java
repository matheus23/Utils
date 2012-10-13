/**
 * 
 */
package org.matheusdev.util.matrix.matrix2;

/**
 * @author matheusdev
 *
 */
public class MatrixN2d extends MappedMatrix2 {

	public double[] values;

	public MatrixN2d(int w, int h) {
		super(w, h);
		values = new double[getSize()];
	}

	public double get(int x, int y) {
		return values[getPosition(x, y)];
	}

	public void set(double val, int x, int y) {
		values[getPosition(x, y)] = val;
	}

}
