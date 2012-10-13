package org.matheusdev.util.matrix;

/**
 * @author matheusdev
 *
 */
public class MatrixNf extends MappedMatrix {
	
	public float[] values;
	
	public MatrixNf(int... dimensions) {
		super(dimensions);
		values = new float[getSize()];
	}
	
	public float get(int... positions) {
		return values[getPosition(positions)];
	}
	
	public void set(float val, int... positions) {
		values[getPosition(positions)] = val;
	}
}
