package org.matheusdev.util.matrix;

/**
 * @author matheusdev
 *
 */
public class PositionOutsideDimensionsException extends RuntimeException {
	private static final long serialVersionUID = 4968868590600781000L;

	public PositionOutsideDimensionsException(int[] dimensions, int[] positions) {
		super(getMsg(dimensions, positions));
	}

	private static String getMsg(int[] dimensions, int[] positions) {
		StringBuilder sb = new StringBuilder("Position is outside the given dimensions.");
		sb.append("Positions:[");
		for (int i = 0; i < dimensions.length; i++) {
			sb.append(dimensions[i] + (
					(i == dimensions.length-1) ? ("]") : (", ")
					));
		}
		sb.append(", Dimensions:[");
		for (int i = 0; i < positions.length; i++) {
			sb.append(positions[i] + (
					(i == positions.length-1) ? ("]") : (", ")
					));
		}
		return sb.toString();
	}

}
