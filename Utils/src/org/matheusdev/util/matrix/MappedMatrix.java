package org.matheusdev.util.matrix;

import org.matheusdev.PosIterationCallback;
import org.matheusdev.util.NumUtils;

/**
 * @author matheusdev
 *
 */
public abstract class MappedMatrix {

	protected int[] dimensions;

	public MappedMatrix(int... dimensions) {
		if (dimensions.length < 1) {
			throw new IllegalArgumentException("dimensions.length < 1");
		}
		for (int dim : dimensions) {
			if (dim == 0) throw new IllegalArgumentException("None of the dimensions is allowed to be 0");
		}
		this.dimensions = dimensions;
	}

	public int getPosition(int... positions) {
		if (positions.length != dimensions.length) {
			throw new IllegalArgumentException("\"positions.length\" != \"dimensions.length\"");
		}
		if (OPTION.PERFORM_CHECKS) {
			for (int i = 0; i < positions.length; i++) {
				if (NumUtils.outside(positions[i], 0, dimensions[i]-1)) {
					throwDimException(positions);
				}
			}
		}
		int pos = 0;
		for (int i = 0; i < positions.length; i++) {
			pos += positions[i] * getOffset(i);
		}
		return pos;
	}

	public int getSize() {
		int size = dimensions[0];
		for (int i = 1; i < dimensions.length; i++) {
			size *= dimensions[i];
		}
		if (size == 0) {
			throw new IllegalArgumentException("WHAAA!?!? ARE YOU CRACY? Do you really want to allocate a matrix, which takes >= memory than 2^32 elements???");
		}
		return size;
	}

	public int getOffset(int dim) {
		int offset = 1;
		for (int i = dim+1; i < dimensions.length; i++) {
			offset *= dimensions[i];
		}
		return offset;
	}

	private void throwDimException(int... positions) {
		throw new PositionOutsideDimensionsException(dimensions, positions);
	}

	public int[] getDimensions() {
		return dimensions;
	}

	public void each(PosIterationCallback callback) {
		cheapEach(callback, new int[dimensions.length], dimensions, 0);
	}

	public void each(PosIterationCallback callback, int[] begin, int[] end, int[] step) {
		each(callback, new int[dimensions.length], dimensions.length, 0, begin, end, step);
	}

	protected void each(PosIterationCallback callback, int[] pos, int dimensions, int dimension, int[] begin, int[] end, int[] step) {
		for (int i = begin[dimension]; i < end[dimension]; i += step[dimension]) {
			pos[dimension] = i;

			if (dimension == dimensions-1) {
				callback.call(pos);
			} else {
				each(callback, pos, dimensions, dimension+1, begin, end, step);
			}
		}
	}

	protected void cheapEach(PosIterationCallback callback, int[] pos, int[] dimensions, int dimension) {
		for (int i = 0; i < dimensions[dimension]; i++) {
			pos[dimension] = i;

			if (dimension == dimensions.length-1) {
				callback.call(pos);
			} else {
				cheapEach(callback, pos, dimensions, dimension+1);
			}
		}
	}

}
