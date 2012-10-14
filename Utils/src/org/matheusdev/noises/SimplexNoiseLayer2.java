package org.matheusdev.noises;

import java.util.Random;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.util.matrix.matrix2.MatrixN2f;

/**
 * @author matheusdev
 *
 */
public class SimplexNoiseLayer2 {

	protected final int width;
	protected final int height;
	protected final MatrixN2f values;
	protected final int density;
	protected final Random rand;
	protected final FloatInterpolation interpolator;

	public SimplexNoiseLayer2(int width, int height, int density, final Random rand, final FloatInterpolation interpolator) {
		this.width = width;
		this.height = height;
		this.values = new MatrixN2f(width, height);
		this.density = density;
		this.rand = rand;
		this.interpolator = interpolator;
	}

	public SimplexNoiseLayer2 gen() {
		for (int x = 0; x < width; x += density) {
			for (int y = 0; y < height; y += density) {
				values.set(rand.nextBoolean() ? -1f : 1f, x, y);
			}
		}

		int x0;
		int y0;
		int x1;
		int y1;

		for (int cellx = 0; cellx < width/density; cellx++) {
			for (int celly = 0; celly < height/density; celly++) {
				x0 = cellx * density;
				y0 = celly * density;
				x1 = x0 + density;
				y1 = y0 + density;

				for (int x = x0; x < x1; x++) {
					for (int y = y0; y < y1; y++) {
						// If it's not the top-left edge of the cell
						if (!(x == x0 && y == y0)) {
							float tx = (float)(x % density) / (float)density;
							float ty = (float)(y % density) / (float)density;

							float edge00 = values.get(x0 % width, y0 % height);
							float edge10 = values.get(x1 % width, y0 % height);
							float edge01 = values.get(x0 % width, y1 % height);
							float edge11 = values.get(x1 % width, y1 % height);

							float v0 = interpolator.interpolate(tx, edge00, 0, edge10, 1);
							float v1 = interpolator.interpolate(tx, edge01, 0, edge11, 1);

							values.set(interpolator.interpolate(ty, v0, 0, v1, 1), x, y);
						}
					}
				}
			}
		}
		return this;
	}

	public MatrixN2f get() {
		return values;
	}

}
