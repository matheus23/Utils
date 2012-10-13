package org.matheusdev.util.matrix.matrix2;

import org.matheusdev.util.NumUtils;
import org.matheusdev.util.matrix.OPTION;
import org.matheusdev.util.matrix.PositionOutsideDimensionsException;

/**
 * @author matheusdev
 *
 */
public abstract class MappedMatrix2 {

	public int w, h;

	public MappedMatrix2(int w, int h) {
		if (w == 0 || h == 0) {
			throw new IllegalArgumentException ("w == 0 || h == 0");
		}
		this.w = w;
		this.h = h;
	}

	public int getSize() {
		return w*h;
	}

	public int getPosition(int x, int y) {
		if (OPTION.PERFORM_CHECKS) {
			if (NumUtils.outside(x, 0, w-1)) throwDimException(x, y);
			if (NumUtils.outside(y, 0, h-1)) throwDimException(x, y);
		}
		return (x*h)+y;
	}

	private void throwDimException(int x, int y) {
		throw new PositionOutsideDimensionsException(
				new int[] { w, h },
				new int[] { x, y });
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}

}
