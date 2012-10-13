package org.matheusdev.util.matrix.matrix3;

import org.matheusdev.util.NumUtils;
import org.matheusdev.util.matrix.OPTION;
import org.matheusdev.util.matrix.PositionOutsideDimensionsException;

/**
 * @author matheusdev
 *
 */
public abstract class MappedMatrix3 {

	public int w, h, d;

	public MappedMatrix3(int w, int h, int d) {
		if (w == 0 || h == 0 || d == 0) {
			throw new IllegalArgumentException ("w == 0 || h == 0 || d == 0");
		}
		this.w = w;
		this.h = h;
		this.d = d;
	}

	public int getSize() {
		return w*h*d;
	}

	public int getPosition(int x, int y, int z) {
		if (OPTION.PERFORM_CHECKS) {
			if (NumUtils.outside(x, 0, w-1)) throwDimException(x, y, z);
			if (NumUtils.outside(y, 0, h-1)) throwDimException(x, y, z);
			if (NumUtils.outside(z, 0, d-1)) throwDimException(x, y, z);
		}
		return (x*h*d)+(y*d)+z;
	}

	private void throwDimException(int x, int y, int z) {
		throw new PositionOutsideDimensionsException(
				new int[] { w, h, d },
				new int[] { x, y, z });
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}

	public int getDepth() {
		return d;
	}

}
