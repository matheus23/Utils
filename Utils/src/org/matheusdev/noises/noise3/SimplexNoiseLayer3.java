package org.matheusdev.noises.noise3;

import java.util.Random;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.util.matrix.matrix3.MatrixN3f;

/**
 * @author matheusdev
 *
 */
public class SimplexNoiseLayer3 implements Noise3 {

	protected final int width;
	protected final int height;
	protected final int depth;
	protected final MatrixN3f values;
	protected final int density;
	protected final Random rand;
	protected final FloatInterpolation interpolator;

	public SimplexNoiseLayer3(int width, int height, int depth, int density, final Random rand, final FloatInterpolation interpolator) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.values = new MatrixN3f(width, height, depth);
		this.density = density;
		this.rand = rand;
		this.interpolator = interpolator;
	}

	public SimplexNoiseLayer3 gen() {
		for (int x = 0; x < width; x += density) {
			for (int y = 0; y < height; y += density) {
				for (int z = 0; z < depth; z += density) {
					values.set(rand.nextBoolean() ? -1f : 1f, x, y, z);
				}
			}
		}

		int x0;
		int y0;
		int z0;
		int x1;
		int y1;
		int z1;

		for (int cellx = 0; cellx < width/density; cellx++) {
			for (int celly = 0; celly < height/density; celly++) {
				for (int cellz = 0; cellz < depth/density; cellz++) {
					x0 = cellx * density;
					y0 = celly * density;
					z0 = cellz * density;
					x1 = x0 + density;
					y1 = y0 + density;
					z1 = z0 + density;

					for (int x = x0; x < x1; x++) {
						for (int y = y0; y < y1; y++) {
							for (int z = z0; z < z1; z++) {
								if (!(x == x0 && y == y0 && z == z0)) {
									float tx = (float)(x % density) / (float)density;
									float ty = (float)(y % density) / (float)density;
									float tz = (float)(z % density) / (float)density;

									float edge000 = values.get(x0 % width, y0 % height, z0 % depth);
									float edge100 = values.get(x1 % width, y0 % height, z0 % depth);
									float edge010 = values.get(x0 % width, y1 % height, z0 % depth);
									float edge110 = values.get(x1 % width, y1 % height, z0 % depth);
									float edge001 = values.get(x0 % width, y0 % height, z1 % depth);
									float edge101 = values.get(x1 % width, y0 % height, z1 % depth);
									float edge011 = values.get(x0 % width, y1 % height, z1 % depth);
									float edge111 = values.get(x1 % width, y1 % height, z1 % depth);

									float v00 = interpolator.interpolate(tx, edge000, 0, edge100, 1);
									float v10 = interpolator.interpolate(tx, edge010, 0, edge110, 1);
									float v01 = interpolator.interpolate(tx, edge001, 0, edge101, 1);
									float v11 = interpolator.interpolate(tx, edge011, 0, edge111, 1);

									float v0 = interpolator.interpolate(ty, v00, 0, v10, 1);
									float v1 = interpolator.interpolate(ty, v01, 0, v11, 1);

									values.set(interpolator.interpolate(tz, v0, 0, v1, 1), x, y, z);
								}
							}
						}
					}
				}
			}
		}
		return this;
	}

	public MatrixN3f get() {
		return values;
	}

    public float get(float x, float y, float z) {
        return values.get((int) x, (int) y, (int) z);
    }

}
