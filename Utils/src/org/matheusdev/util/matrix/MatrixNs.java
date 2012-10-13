package org.matheusdev.util.matrix;

/**
 * @author matheusdev
 *
 */
public class MatrixNs extends MappedMatrix {
	
	public short[] values;
	
	public MatrixNs(int... dimensions) {
		super(dimensions);
		values = new short[getSize()];
	}
	
	public short get(int... positions) {
		return values[getPosition(positions)];
	}
	
	public void set(short val, int... positions) {
		values[getPosition(positions)] = val;
	}
}
