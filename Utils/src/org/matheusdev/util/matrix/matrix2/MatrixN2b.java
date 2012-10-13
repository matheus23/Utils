/**
 * 
 */
package org.matheusdev.util.matrix.matrix2;

/**
 * @author matheusdev
 *
 */
public class MatrixN2b extends MappedMatrix2 {

	public byte[] values;

	public MatrixN2b(int w, int h) {
		super(w, h);
		values = new byte[getSize()];
	}

	public byte get(int x, int y) {
		return values[getPosition(x, y)];
	}

	public void set(byte val, int x, int y) {
		values[getPosition(x, y)] = val;
	}

}
