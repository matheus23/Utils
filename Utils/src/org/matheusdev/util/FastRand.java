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

	protected long randLong() {
		seed ^= (seed << 21);
		seed ^= (seed >>> 35);
		seed ^= (seed << 4);
		return seed;
	}

	public int randInt() {
		return (int) randLong();
	}

	public int randAbsInt() {
		return Math.abs((int) randLong());
	}

	public double randomDouble() {
		return randLong() / (Double.MAX_VALUE - 1d);
	}

	public float randomFloat() {
		return randLong() / (Float.MAX_VALUE - 1f);
	}

	public boolean randBool() {
		return randLong() > 0;
	}

	protected static long getSeed() {
		return System.currentTimeMillis() + System.nanoTime();
	}

}
