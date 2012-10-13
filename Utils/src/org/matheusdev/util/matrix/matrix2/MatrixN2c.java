/**
 * 
 */
package org.matheusdev.util.matrix.matrix2;

/**
 * @author matheusdev
 *
 */
public class MatrixN2c extends MappedMatrix2 {

	public char[] values;

	public MatrixN2c(int w, int h) {
		super(w, h);
		values = new char[getSize()];
	}

	public char get(int x, int y) {
		return values[getPosition(x, y)];
	}

	public void set(char val, int x, int y) {
		values[getPosition(x, y)] = val;
	}

}
