package org.matheusdev.util.matrix;

/**
 * @author matheusdev
 *
 */
public class MatrixNb extends MappedMatrix {
	
	public byte[] values;
	
	public MatrixNb(int... dimensions) {
		super(dimensions);
		values = new byte[getSize()];
	}
	
	public byte get(int... positions) {
		return values[getPosition(positions)];
	}
	
	public void set(byte val, int... positions) {
		values[getPosition(positions)] = val;
	}
}
