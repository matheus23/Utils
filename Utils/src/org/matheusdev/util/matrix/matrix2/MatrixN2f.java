/**
 * 
 */
package org.matheusdev.util.matrix.matrix2;

/**
 * @author matheusdev
 *
 */
public class MatrixN2f extends MappedMatrix2 {

	public float[] values;

	public MatrixN2f(int w, int h) {
		super(w, h);
		values = new float[getSize()];
	}

	public float get(int x, int y) {
		return values[getPosition(x, y)];
	}

	public void set(float val, int x, int y) {
		values[getPosition(x, y)] = val;
	}

}
