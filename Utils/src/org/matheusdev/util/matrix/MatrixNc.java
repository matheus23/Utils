package org.matheusdev.util.matrix;

/**
 * @author matheusdev
 *
 */
public class MatrixNc extends MappedMatrix {
	
	public char[] values;
	
	public MatrixNc(int... dimensions) {
		super(dimensions);
		values = new char[getSize()];
	}
	
	public char get(int... positions) {
		return values[getPosition(positions)];
	}
	
	public void set(char val, int... positions) {
		values[getPosition(positions)] = val;
	}
}
