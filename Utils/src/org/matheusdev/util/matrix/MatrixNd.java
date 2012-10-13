package org.matheusdev.util.matrix;

/**
 * @author matheusdev
 *
 */
public class MatrixNd extends MappedMatrix {
	
	public double[] values;
	
	public MatrixNd(int... dimensions) {
		super(dimensions);
		values = new double[getSize()];
	}
	
	public double get(int... positions) {
		return values[getPosition(positions)];
	}
	
	public void set(double val, int... positions) {
		values[getPosition(positions)] = val;
	}
}
