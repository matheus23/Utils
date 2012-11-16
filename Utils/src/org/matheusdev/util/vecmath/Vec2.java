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
package org.matheusdev.util.vecmath;

import java.nio.FloatBuffer;

/**
 * @author matheusdev
 *
 */
public class Vec2 implements FloatBuffable<Vec2> {

	public float x;
	public float y;

	public Vec2() {
		this(0, 0);
	}

	public Vec2(float x, float y) {
		set(x, y);
	}

	public Vec2 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vec2 set(Vec2 toCopy) {
		return set(toCopy.x, toCopy.y);
	}

	public Vec2 scale(float factor) {
		return scale(factor, factor);
	}

	public Vec2 scale(float xs, float ys) {
		this.x *= xs;
		this.y *= ys;
		return this;
	}

	public float len() {
		return (float) Math.sqrt(sqLen());
	}

	public float sqLen() {
		return x * x + y * y;
	}

	public float directionRad() {
		return (float) Math.atan2(y, x);
	}

	public float directionDeg() {
		return (float) Math.toDegrees(directionRad());
	}

	public Vec2 normalize() {
		return scale(len());
	}

	public Vec2 normalize(Vec2 dest) {
		dest = getDest(dest);

		dest.set(this);
		dest.normalize();

		return dest;
	}

	public Vec2 translate(float xt, float yt) {
		this.x += xt;
		this.y += yt;
		return this;
	}

	public Vec2 negate(Vec2 dest) {
		dest = getDest(dest);

		dest.x = -x;
		dest.y = -y;

		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.FloatBuffable#load(java.nio.FloatBuffer)
	 */
	@Override
	public Vec2 load(FloatBuffer buf) {
		this.x = buf.get();
		this.y = buf.get();
		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.FloatBuffable#store(java.nio.FloatBuffer)
	 */
	@Override
	public Vec2 store(FloatBuffer buf) {
		buf.put(x).put(y);
		return this;
	}

	public static Vec2 add(Vec2 left, Vec2 right, Vec2 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x + right.x;
		dest.y = left.y + right.y;

		return dest;
	}

	public static Vec2 sub(Vec2 left, Vec2 right, Vec2 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x - right.x;
		dest.y = left.y - right.y;

		return dest;
	}

	public static Vec2 mul(Vec2 left, Vec2 right, Vec2 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x * right.x;
		dest.y = left.y * right.y;

		return dest;
	}

	public static Vec2 div(Vec2 left, Vec2 right, Vec2 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x / right.x;
		dest.y = left.y / right.y;

		return dest;
	}

	public static float dot(Vec2 left, Vec2 right) {
		return left.x * right.x + left.y * right.y;
	}

	public static float angleRad(Vec2 v1, Vec2 v2) {
		float d = dot(v1, v2) / (v1.len() * v2.len());

		if (d < -1f) {
			d = -1f;
		} else if (d > 1.0f) {
			d = 1.0f;
		}

		return (float) Math.acos(d);
	}

	public static float angleDeg(Vec2 v1, Vec2 v2) {
		return (float) Math.toDegrees(angleRad(v1, v2));
	}

	protected static Vec2 getDest(Vec2 dest) {
		return dest == null ? new Vec2() : dest;
	}

}
