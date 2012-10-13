/**
 * 
 */
package org.matheusdev.util.matrix.matrix2;

/**
 * @author matheusdev
 *
 */
public class MatrixN2s extends MappedMatrix2 {

	public short[] values;

	public MatrixN2s(int w, int h) {
		super(w, h);
		values = new short[getSize()];
	}

	public short get(int x, int y) {
		return values[getPosition(x, y)];
	}

	public void set(short val, int x, int y) {
		values[getPosition(x, y)] = val;
	}

}
