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
public class Vec3d implements DoubleBuffable<Vec3d> {

	// Pooling:
	private static final Vec3d POOL = new Vec3d();
	public static Vec3d getPool() {
		return POOL.set(0, 0, 0);
	}

	public double x;
	public double y;
	public double z;

	public Vec3d() {
		this(0, 0, 0);
	}

	public Vec3d(double x, double y, double z) {
		set(x, y, z);
	}

	public Vec3d set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vec3d set(Vec3d toCopy) {
		return set(toCopy.x, toCopy.y, toCopy.z);
	}

	public Vec3d scale(double factor) {
		return scale(factor, factor, factor);
	}

	public Vec3d scale(double xs, double ys, double zs) {
		this.x *= xs;
		this.y *= ys;
		this.z *= zs;
		return this;
	}

	public Vec3d translate(double xt, double yt, double zt) {
		this.x += xt;
		this.y += yt;
		this.z += zt;
		return this;
	}

	public Vec3d negate(Vec3d dest) {
		dest = getDest(dest);

		dest.x = -x;
		dest.y = -y;
		dest.z = -z;

		return dest;
	}

	public double len() {
		return Math.sqrt(sqLen());
	}

	public double sqLen() {
		return x * x + y * y + z * z;
	}

	public Vec3d normalize() {
		return scale(len());
	}

	public Vec3d normalize(Vec3d dest) {
		dest = getDest(dest);

		dest.set(this);
		dest.normalize();

		return dest;
	}

	public Vec3d transform(Mat4d mat) {
		return transform(this, mat, this);
	}

	public Vec3d transform(Mat3d mat) {
		return transform(this, mat, this);
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.doubleBuffable#load(java.nio.doubleBuffer)
	 */
	@Override
	public Vec3d load(DoubleBuffer buf) {
		this.x = buf.get();
		this.y = buf.get();
		this.y = buf.get();
		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.doubleBuffable#store(java.nio.doubleBuffer)
	 */
	@Override
	public Vec3d store(DoubleBuffer buf) {
		buf.put(x).put(y).put(z);
		return this;
	}

	public int getRGB() {
		int r = ((int) (x * 255)) & 0xFF;
		int g = ((int) (y * 255)) & 0xFF;
		int b = ((int) (z * 255)) & 0xFF;
		return b | (g << 8) | (r << 16) | 0xFF000000;
	}

	public static Vec3d cross(Vec3d left, Vec3d right, Vec3d dest) {
		dest = getDest(dest);

		double x = left.y * right.z - left.z * right.y;
		double y = right.x * left.z - right.z * left.x;
		double z = left.x * right.y - left.y * right.x;

		dest.x = x;
		dest.y = y;
		dest.z = z;

		return dest;
	}

	public static Vec3d add(Vec3d left, Vec3d right, Vec3d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x + right.x;
		dest.y = left.y + right.y;
		dest.z = left.z + right.z;

		return dest;
	}

	public static Vec3d sub(Vec3d left, Vec3d right, Vec3d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x - right.x;
		dest.y = left.y - right.y;
		dest.z = left.z - right.z;

		return dest;
	}

	public static Vec3d mul(Vec3d left, Vec3d right, Vec3d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x * right.x;
		dest.y = left.y * right.y;
		dest.z = left.z * right.z;

		return dest;
	}

	public static Vec3d div(Vec3d left, Vec3d right, Vec3d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x / right.x;
		dest.y = left.y / right.y;
		dest.z = left.z / right.z;

		return dest;
	}

	public static double dot(Vec3d left, Vec3d right) {
		return left.x * right.x + left.y * right.y + left.z * right.z;
	}

	public static double angleRad(Vec3d v1, Vec3d v2) {
		double d = dot(v1, v2) / (v1.len() * v2.len());

		if (d < -1f) {
			d = -1f;
		} else if (d > 1.0f) {
			d = 1.0f;
		}

		return Math.acos(d);
	}

	public static double angleDeg(Vec3d v1, Vec3d v2) {
		return Math.toDegrees(angleRad(v1, v2));
	}

	public static Vec3d transform(Vec3d vec, Mat3d mat, Vec3d dest) {
		dest = getDest(dest);

		Vec4d vec4 = Vec4d.getPool().set(vec.x, vec.y, vec.z, 1);
		vec4.transform(mat);
		dest.x = vec4.x / vec4.w;
		dest.y = vec4.y / vec4.w;

		return dest;
	}

	public static Vec3d transform(Vec3d vec, Mat4d mat, Vec3d dest) {
		dest = getDest(dest);

		Vec4d vec4 = Vec4d.getPool().set(vec.x, vec.y, vec.z, 1);
		vec4.transform(mat);
		dest.x = vec4.x / vec4.w;
		dest.y = vec4.y / vec4.w;

		return dest;
	}

	protected static Vec3d getDest(Vec3d dest) {
		return dest == null ? new Vec3d() : dest;
	}

}
