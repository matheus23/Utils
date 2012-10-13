package org.matheusdev.util.matrix.matrix3;

/**
 * @author matheusdev
 *
 */
public class MatrixN3o<E> extends MappedMatrix3 {

	public E[] values;

	@SuppressWarnings("unchecked")
	public MatrixN3o(int w, int h, int d) {
		super(w, h, d);
		values = (E[]) new Object[getSize()];
	}

	public E get(int x, int y, int z) {
		return values[getPosition(x, y, z)];
	}

	public void set(E val, int x, int y, int z) {
		values[getPosition(x, y, z)] = val;
	}

}