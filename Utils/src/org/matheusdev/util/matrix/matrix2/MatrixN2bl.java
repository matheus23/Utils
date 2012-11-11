/**
 *
 */
package org.matheusdev.util.matrix.matrix2;

/**
 * @author matheusdev
 *
 */
public class MatrixN2bl extends MappedMatrix2 {

	private static byte[] masks = {
		0b00000001,
		0b00000010,
		0b00000100,
		0b00001000,
		0b00010000,
		0b00100000,
		0b01000000,
		(byte) 0b10000000
	};

	protected static int createWidth(int w) {
		return w + (w % 8);
	}

	public byte[] values;

	public MatrixN2bl(int w, int h) {
		super(createWidth(w), h);
		values = new byte[getSize() / 8 + 1];
	}

	public boolean get(int x, int y) {
		return extractBit(x % 8, values[getPosition(x / 8, y)]);
	}

	public void set(boolean val, int x, int y) {
		int pos = getPosition(x / 8, y);
		values[pos] = injectBit(x % 8, values[pos], val);
	}

	protected boolean extractBit(int bit, byte bits) {
		return (bits & masks[bit]) == masks[bit];
	}

	protected byte injectBit(int bit, byte bits, boolean set) {
		if (set) {
			return (byte) (bits | masks[bit]);
		} else {
			return (byte) (bits & ~masks[bit]);
		}
	}

}
