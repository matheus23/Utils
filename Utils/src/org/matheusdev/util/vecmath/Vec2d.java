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

import java.nio.DoubleBuffer;


/**
 * @author matheusdev
 *
 */
public class Vec2d implements DoubleBuffable<Vec2d> {

	// Pooling:
	private static final Vec2d POOL = new Vec2d();
	public static Vec2d getPool() {
		return POOL.set(0, 0);
	}

	public double x;
	public double y;

	public Vec2d() {
		this(0, 0);
	}

	public Vec2d(double x, double y) {
		set(x, y);
	}

	public Vec2d set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vec2d set(Vec2d toCopy) {
		return set(toCopy.x, toCopy.y);
	}

	public Vec2d scale(double factor) {
		return scale(factor, factor);
	}

	public Vec2d scale(double xs, double ys) {
		this.x *= xs;
		this.y *= ys;
		return this;
	}

	public double len() {
		return Math.sqrt(sqLen());
	}

	public double sqLen() {
		return x * x + y * y;
	}

	public double directionRad() {
		return Math.atan2(y, x);
	}

	public double directionDeg() {
		return Math.toDegrees(directionRad());
	}

	public Vec2d normalize() {
		double len = len();
		this.x /= len;
		this.y /= len;
		return this;
	}

	public Vec2d normalize(Vec2d dest) {
		dest = getDest(dest);

		dest.set(this);
		dest.normalize();

		return dest;
	}

	public Vec2d transform(Mat4d mat) {
		return transform(this, mat, this);
	}

	public Vec2d transform(Mat3d mat) {
		return transform(this, mat, this);
	}

	public Vec2d translate(double xt, double yt) {
		this.x += xt;
		this.y += yt;
		return this;
	}

	public Vec2d negate(Vec2d dest) {
		dest = getDest(dest);

		dest.x = -x;
		dest.y = -y;

		return this;
	}

	public Vec2d perpRight() {
		return perpRight(this, this);
	}

	public Vec2d perpLeft() {
		return perpLeft(this, this);
	}

	public Vec2d rotateRad(double alpha) {
		return rotateRad(this, alpha, this);
	}

	public Vec2d rotateDeg(double alpha) {
		return rotateDeg(this, alpha, this);
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.doubleBuffable#load(java.nio.doubleBuffer)
	 */
	@Override
	public Vec2d load(DoubleBuffer buf) {
		this.x = buf.get();
		this.y = buf.get();
		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.doubleBuffable#store(java.nio.doubleBuffer)
	 */
	@Override
	public Vec2d store(DoubleBuffer buf) {
		buf.put(x).put(y);
		return this;
	}

	public static Vec2d add(Vec2d left, Vec2d right, Vec2d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x + right.x;
		dest.y = left.y + right.y;

		return dest;
	}

	public static Vec2d sub(Vec2d left, Vec2d right, Vec2d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x - right.x;
		dest.y = left.y - right.y;

		return dest;
	}

	public static Vec2d mul(Vec2d left, Vec2d right, Vec2d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x * right.x;
		dest.y = left.y * right.y;

		return dest;
	}

	public static Vec2d div(Vec2d left, Vec2d right, Vec2d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x / right.x;
		dest.y = left.y / right.y;

		return dest;
	}

	public static double dot(Vec2d left, Vec2d right) {
		return left.x * right.x + left.y * right.y;
	}

	public static double angleRad(Vec2d v1, Vec2d v2) {
		double d = dot(v1, v2) / (v1.len() * v2.len());

		if (d < -1f) {
			d = -1f;
		} else if (d > 1.0f) {
			d = 1.0f;
		}

		return Math.acos(d);
	}

	public static double angleDeg(Vec2d v1, Vec2d v2) {
		return Math.toDegrees(angleRad(v1, v2));
	}

	public static Vec2d transform(Vec2d vec, Mat3d mat, Vec2d dest) {
		dest = getDest(dest);

		Vec4d vec4 = Vec4d.getPool().set(vec.x, vec.y, 0, 1);
		vec4.transform(mat);
		dest.x = vec4.x / vec4.w;
		dest.y = vec4.y / vec4.w;

		return dest;
	}

	public static Vec2d transform(Vec2d vec, Mat4d mat, Vec2d dest) {
		dest = getDest(dest);

		Vec4d vec4 = Vec4d.getPool().set(vec.x, vec.y, 0, 1);
		vec4.transform(mat);
		dest.x = vec4.x / vec4.w;
		dest.y = vec4.y / vec4.w;

		return dest;
	}

	public static Vec2d perpRight(Vec2d vec, Vec2d dest) {
		dest = getDest(dest);

		double x = vec.x;
		double y = vec.y;

		dest.x = y;
		dest.y = -x;

		return dest;
	}

	public static Vec2d perpLeft(Vec2d vec, Vec2d dest) {
		dest = getDest(dest);

		double x = vec.x;
		double y = vec.y;

		dest.x = -y;
		dest.y = x;

		return dest;
	}

	public static Vec2d rotateRad(Vec2d vec, double alpha, Vec2d dest) {
		dest = getDest(dest);

		double c = Math.cos(alpha);
		double s = Math.sin(alpha);

		double x = vec.x * c - vec.y * s;
		double y = vec.x * s + vec.y * c;

		dest.x = x;
		dest.y = y;

		return dest;
	}

	public static Vec2d rotateDeg(Vec2d vec, double alpha, Vec2d dest) {
		return rotateRad(vec, Math.toRadians(alpha), dest);
	}

	protected static Vec2d getDest(Vec2d dest) {
		return dest == null ? new Vec2d() : dest;
	}

}
