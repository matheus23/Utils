package org.matheusdev.util.matrix;

/**
 * @author matheusdev
 *
 */
public class MatrixNi extends MappedMatrix {
	
	public int[] values;
	
	public MatrixNi(int... dimensions) {
		super(dimensions);
		values = new int[getSize()];
	}
	
	public int get(int... positions) {
		return values[getPosition(positions)];
	}
	
	public void set(int val, int... positions) {
		values[getPosition(positions)] = val;
	}
}
