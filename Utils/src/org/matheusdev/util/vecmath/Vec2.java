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

	// Pooling:
	private static final Vec2 POOL = new Vec2();
	public static Vec2 getPool() {
		return POOL.set(0, 0);
	}

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
		float len = len();
		this.x /= len;
		this.y /= len;
		return this;
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

	public Vec2 transform(Mat3 mat) {
		return transform(this, mat, this);
	}

	public Vec2 transform(Mat4 mat) {
		return transform(this, mat, this);
	}

	public Vec2 perpRight() {
		return perpRight(this, this);
	}

	public Vec2 perpLeft() {
		return perpLeft(this, this);
	}

	public Vec2 rotateRad(float alpha) {
		return rotateRad(this, alpha, this);
	}

	public Vec2 rotateDeg(float alpha) {
		return rotateDeg(this, alpha, this);
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

	public static Vec2 transform(Vec2 vec, Mat3 mat, Vec2 dest) {
		dest = getDest(dest);

		Vec4 vec4 = Vec4.getPool().set(vec.x, vec.y, 0, 1);
		vec4.transform(mat);
		dest.x = vec4.x / vec4.w;
		dest.y = vec4.y / vec4.w;

		return dest;
	}

	public static Vec2 transform(Vec2 vec, Mat4 mat, Vec2 dest) {
		dest = getDest(dest);

		Vec4 vec4 = Vec4.getPool().set(vec.x, vec.y, 0, 1);
		vec4.transform(mat);
		dest.x = vec4.x / vec4.w;
		dest.y = vec4.y / vec4.w;

		return dest;
	}

	public static Vec2 perpRight(Vec2 vec, Vec2 dest) {
		dest = getDest(dest);

		float x = vec.x;
		float y = vec.y;

		dest.x = y;
		dest.y = -x;

		return dest;
	}

	public static Vec2 perpLeft(Vec2 vec, Vec2 dest) {
		dest = getDest(dest);

		float x = vec.x;
		float y = vec.y;

		dest.x = -y;
		dest.y = x;

		return dest;
	}

	public static Vec2 rotateRad(Vec2 vec, float alpha, Vec2 dest) {
		dest = getDest(dest);

		float c = (float) Math.cos(alpha);
		float s = (float) Math.sin(alpha);

		float x = vec.x * c - vec.y * s;
		float y = vec.x * s + vec.y * c;

		dest.x = x;
		dest.y = y;

		return dest;
	}

	public static Vec2 rotateDeg(Vec2 vec, float alpha, Vec2 dest) {
		return rotateRad(vec, (float) Math.toRadians(alpha), dest);
	}

	protected static Vec2 getDest(Vec2 dest) {
		return dest == null ? new Vec2() : dest;
	}

}
