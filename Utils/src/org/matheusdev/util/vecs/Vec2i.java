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
package org.matheusdev.util.vecs;

/**
 * @author matheusdev
 *
 */
public class Vec2i {
	protected static final Vec2i instance = new Vec2i(0, 0);

	public static Vec2i get(int x, int y) {
		return instance.set(x, y);
	}

	public static Vec2i get() {
		return get(0, 0);
	}

	public int x;
	public int y;

	public Vec2i(int x, int y) {
		set(x, y);
	}

	public Vec2i(Vec2i other) {
		set(other.x, other.y);
	}

	public Vec2i set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != this.getClass()) return false;
		Vec2i other = (Vec2i) obj;
		if (this.x == other.x && this.y == other.y) return true;
		return false;
	}

	@Override
	public int hashCode() {
		return hashCode(x, y);
	}

	public static int hashCode(int x, int y) {
		final int prime = 31;
		int hashcode = 1;
		hashcode = prime * hashcode + x;
		hashcode = prime * hashcode + y;

		return hashcode;
	}

}
