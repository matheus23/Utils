/**
 * 
 */
package org.matheusdev.util.matrix.matrix2;

/**
 * @author matheusdev
 *
 */
public class MatrixN2o<E> extends MappedMatrix2 {

	public E[] values;

	@SuppressWarnings("unchecked")
	public MatrixN2o(int w, int h) {
		super(w, h);
		values = (E[]) new Object[getSize()];
	}

	public E get(int x, int y) {
		return values[getPosition(x, y)];
	}

	public void set(E val, int x, int y) {
		values[getPosition(x, y)] = val;
	}

}
