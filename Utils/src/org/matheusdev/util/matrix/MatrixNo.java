package org.matheusdev.util.matrix;

/**
 * @author matheusdev
 *
 */
public class MatrixNo<E> extends MappedMatrix {
	
	public E[] values;
	
	@SuppressWarnings("unchecked")
	public MatrixNo(int... dimensions) {
		super(dimensions);
		values = (E[]) new Object[getSize()];
	}
	
	public E get(int... positions) {
		return values[getPosition(positions)];
	}
	
	public void set(E val, int... positions) {
		values[getPosition(positions)] = val;
	}
}
