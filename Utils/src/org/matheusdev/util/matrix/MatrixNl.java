package org.matheusdev.util.matrix;

/**
 * @author matheusdev
 *
 */
public class MatrixNl extends MappedMatrix {
	
	public long[] values;
	
	public MatrixNl(int... dimensions) {
		super(dimensions);
		values = new long[getSize()];
	}
	
	public long get(int... positions) {
		return values[getPosition(positions)];
	}
	
	public void set(long val, int... positions) {
		values[getPosition(positions)] = val;
	}
}
