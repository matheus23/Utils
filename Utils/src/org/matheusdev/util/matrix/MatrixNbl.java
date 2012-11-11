package org.matheusdev.util.matrix;

/**
 * @author matheusdev
 *
 */
public class MatrixNbl extends MappedMatrix {

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

	protected static int[] createDimensions(int... dims) {
		dims[0] = dims[0] + (dims[0] % 8);
		return dims;
	}

	public byte[] values;

	public MatrixNbl(int... dimensions) {
		super(createDimensions(dimensions));
		values = new byte[getSize() / 8 + 1];
	}

	public boolean get(int... positions) {
		int savex = positions[0];
		positions[0] = positions[0] / 8;
		int pos = getPosition(positions);
		positions[0] = savex;
		return extractBit(positions[0] % 8, values[pos]);
	}

	public void set(boolean val, int... positions) {
		int savex = positions[0];
		positions[0] = positions[0] / 8;
		int pos = getPosition(positions);
		positions[0] = savex;
		values[pos] = injectBit(positions[0] % 8, values[pos], val);
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
