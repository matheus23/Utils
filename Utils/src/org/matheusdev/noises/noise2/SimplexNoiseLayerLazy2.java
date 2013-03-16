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
package org.matheusdev.noises.noise2;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.util.FastRand;
import org.matheusdev.util.vecs.Vec2i;

/**
 * @author matheusdev
 *
 */
public class SimplexNoiseLayerLazy2 implements Noise2 {

	protected final long seed;
	protected final int density;
	protected final FastRand rand;
	protected final FloatInterpolation interpolator;

	public SimplexNoiseLayerLazy2(final int density, final FloatInterpolation interpolator) {
		this(new FastRand().randLong(), density, interpolator);
	}

	public SimplexNoiseLayerLazy2(final long seed, final int density, final FloatInterpolation interpolator) {
		this.density = density;
		this.seed = seed;
		this.rand = new FastRand();
		this.interpolator = interpolator;
	}

	public float get(float x, float y) {
		final int cellX0 = ((int) x / density);
		final int cellY0 = ((int) y / density);
		final int cellX1 = ((int) x / density) + 1;
		final int cellY1 = ((int) y / density) + 1;

		final float value00 = getRand(cellX0, cellY0).randBool() ? -1f : 1f;
		final float value10 = getRand(cellX1, cellY0).randBool() ? -1f : 1f;
		final float value11 = getRand(cellX1, cellY1).randBool() ? -1f : 1f;
		final float value01 = getRand(cellX0, cellY1).randBool() ? -1f : 1f;

		final float relativeX = x - (cellX0 * density);
		final float relativeY = y - (cellY0 * density);
        final float timeX = relativeX / density;
		final float timeY = relativeY / density;

		final float interpolationHoriz0 =
				interpolator.interpolate(
						timeX, value00, 0, value10, 1);
		final float interpolationHoriz1 =
				interpolator.interpolate(
						timeX, value01, 0, value11, 1);
		final float interpolationVert =
				interpolator.interpolate(
						timeY, interpolationHoriz0, 0f, interpolationHoriz1, 1f);

        return interpolationVert;
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

    private FastRand getRand(int x, int y) {
        rand.setSeed(hashWang((x << 17) ^ y) + seed);
        return rand;
    }

}
