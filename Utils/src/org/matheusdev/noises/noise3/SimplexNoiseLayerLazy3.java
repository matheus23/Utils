/*
 * Copyright (c) 2012 matheusdev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.matheusdev.noises.noise3;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.noises.noise2.Noise2;
import org.matheusdev.util.FastRand;

/**
 * @author matheusdev
 *
 */
public class SimplexNoiseLayerLazy3 implements Noise3 {

	protected final long seed;
	protected final int density;
	protected final FastRand rand;
	protected final FloatInterpolation interpolator;

	public SimplexNoiseLayerLazy3(final int density, final FloatInterpolation interpolator) {
		this(new FastRand().randLong(), density, interpolator);
	}

	public SimplexNoiseLayerLazy3(final long seed, final int density, final FloatInterpolation interpolator) {
		this.density = density;
		this.seed = seed;
		this.rand = new FastRand();
		this.interpolator = interpolator;
	}

	public float get(float x, float y, float z) {
		final int cellX0 = ((int) x / density);
		final int cellY0 = ((int) y / density);
        final int cellZ0 = ((int) z / density);
		final int cellX1 = ((int) x / density) + 1;
		final int cellY1 = ((int) y / density) + 1;
        final int cellZ1 = ((int) z / density) + 1;

		final float value000 = getRand(cellX0, cellY0, cellZ0).randBool() ? -1f : 1f;
		final float value100 = getRand(cellX1, cellY0, cellZ0).randBool() ? -1f : 1f;
		final float value110 = getRand(cellX1, cellY1, cellZ0).randBool() ? -1f : 1f;
		final float value010 = getRand(cellX0, cellY1, cellZ0).randBool() ? -1f : 1f;
        final float value001 = getRand(cellX0, cellY0, cellZ1).randBool() ? -1f : 1f;
        final float value101 = getRand(cellX1, cellY0, cellZ1).randBool() ? -1f : 1f;
        final float value111 = getRand(cellX1, cellY1, cellZ1).randBool() ? -1f : 1f;
        final float value011 = getRand(cellX0, cellY1, cellZ1).randBool() ? -1f : 1f;

		final float relativeX = x - (cellX0 * density);
		final float relativeY = y - (cellY0 * density);
        final float relativeZ = z - (cellZ0 * density);
        final float timeX = relativeX / density;
		final float timeY = relativeY / density;
        final float timeZ = relativeZ / density;

		final float interpolationHoriz0 =
				interpolator.interpolate(
						timeX, value000, 0, value100, 1);
		final float interpolationHoriz1 =
				interpolator.interpolate(
						timeX, value010, 0, value110, 1);
        final float interpolationHoriz2 =
                interpolator.interpolate(
                        timeX, value001, 0, value101, 1);
        final float interpolationHoriz3 =
                interpolator.interpolate(
                        timeX, value011, 0, value111, 1);
		final float interpolationVert0 =
				interpolator.interpolate(
						timeY, interpolationHoriz0, 0f, interpolationHoriz1, 1f);
        final float interpolationVert1 =
                interpolator.interpolate(
                        timeY, interpolationHoriz2, 0f, interpolationHoriz3, 1f);
        final float interpolation =
                interpolator.interpolate(
                        timeY, interpolationVert0, 0f, interpolationVert1, 1f);

        return interpolation;
	}

    private final static long hashWang(long key) {
        key = (~key) + (key << 21); // key = (key << 21) - key - 1;
        key = key ^ (key >>> 24);
        key = (key + (key << 3)) + (key << 8); // key * 265
        key = key ^ (key >>> 14);
        key = (key + (key << 2)) + (key << 4); // key * 21
        key = key ^ (key >>> 28);
        key = key + (key << 31);

        return key;
    }

    private FastRand getRand(int x, int y, int z) {
        rand.setSeed(hashWang((x << 17) ^ (y << 13) ^ z) + seed);
        return rand;
    }

}
