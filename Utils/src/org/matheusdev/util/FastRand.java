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
package org.matheusdev.util;


/**
 * @author matheusdev
 *
 */
public class FastRand {

	private long seed;

	public FastRand(long seed) {
		this.seed = seed;
	}

	public FastRand() {
		this.seed = getSeed();
	}

	protected static long getSeed() {
		return System.currentTimeMillis() + System.nanoTime();
	}

	/* RANDOM INT AND LONG: */

	public long randLong() {
		seed ^= (seed << 21);
		seed ^= (seed >>> 35);
		seed ^= (seed << 4);
		return seed;
	}

	public int randInt() {
		return (int) randLong();
	}

	public long randAbsLong() {
		return Math.abs(randLong());
	}

	public int randAbsInt() {
		return Math.abs((int) randLong());
	}

	public long randLong(long max) {
		return randAbsLong() % max;
	}

	public int randInt(int max) {
		return randAbsInt() % max;
	}

	public long randLong(long min, long max) {
		return randLong(max - min) + min;
	}

	public int randInt(int min, int max) {
		return randInt(max - min) + min;
	}

	/* RANDOM DOUBLE AND FLOAT */

	public double randDouble() {
		return randLong() / (Long.MAX_VALUE - 1d);
	}

	public float randFloat() {
		return randLong() / (Long.MAX_VALUE - 1f);
	}

	public double randAbsDouble() {
		return (randDouble() + 1.0) / 2.0;
	}

	public float randAbsFloat() {
		return (randFloat() + 1.0f) / 2.0f;
	}

	public double randDouble(double min, double max) {
		return randAbsDouble() * (max - min) + min;
	}

	public float randFloat(float min, float max) {
		return randAbsFloat() * (max - min) + min;
	}

	/* OTHERS */

	public boolean randBool() {
		return randLong() > 0;
	}

}
