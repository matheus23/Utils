package org.matheusdev.util.matrix;

/**
 * @author matheusdev
 *
 */
public class MatrixNbl extends MappedMatrix {
	
	public boolean[] values;
	
	public MatrixNbl(int... dimensions) {
		super(dimensions);
		values = new boolean[getSize()];
	}
	
	public boolean get(int... positions) {
		return values[getPosition(positions)];
	}
	
	public void set(boolean val, int... positions) {
		values[getPosition(positions)] = val;
	}
}
