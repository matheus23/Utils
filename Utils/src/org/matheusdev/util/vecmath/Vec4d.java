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
public class Vec4d implements DoubleBuffable<Vec4d> {

	public double x;
	public double y;
	public double z;
	public double w;

	public Vec4d() {
		this(0, 0, 0, 0);
	}

	public Vec4d(double x, double y, double z, double w) {
		set(x, y, z, w);
	}

	public Vec4d set(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	public Vec4d set(Vec4d toCopy) {
		return set(toCopy.x, toCopy.y, toCopy.z, toCopy.w);
	}

	public Vec4d scale(double factor) {
		return scale(factor, factor, factor, factor);
	}

	public Vec4d scale(double xs, double ys, double zs, double ws) {
		this.x *= xs;
		this.y *= ys;
		this.z *= zs;
		this.z *= ws;
		return this;
	}

	public Vec4d translate(double xt, double yt, double zt, double wt) {
		this.x += xt;
		this.y += yt;
		this.z += zt;
		this.z += wt;
		return this;
	}

	public Vec4d negate(Vec4d dest) {
		dest = getDest(dest);

		dest.x = -x;
		dest.y = -y;
		dest.z = -z;
		dest.w = -w;

		return dest;
	}

	public double len() {
		return Math.sqrt(sqLen());
	}

	public double sqLen() {
		return x * x + y * y + z * z + w * w;
	}

	public Vec4d normalize() {
		return scale(len());
	}

	public Vec4d normalize(Vec4d dest) {
		dest = getDest(dest);

		dest.set(this);
		dest.normalize();

		return dest;
	}

	public Vec4d transform(Mat3 mat) {
		return transform(mat, this, this);
	}

	public Vec4d transform(Mat4 mat) {
		return transform(mat, this, this);
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.doubleBuffable#load(java.nio.doubleBuffer)
	 */
	@Override
	public Vec4d load(DoubleBuffer buf) {
		this.x = buf.get();
		this.y = buf.get();
		this.y = buf.get();
		this.w = buf.get();
		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.doubleBuffable#store(java.nio.doubleBuffer)
	 */
	@Override
	public Vec4d store(DoubleBuffer buf) {
		buf.put(x).put(y).put(z).put(w);
		return this;
	}

	public int getRGB() {
		int r = ((int) (x * 255)) & 0xFF;
		int g = ((int) (y * 255)) & 0xFF;
		int b = ((int) (z * 255)) & 0xFF;
		int a = ((int) (w * 255)) & 0xFF;
		return b | (g << 8) | (r << 16) | (a << 24);
	}

	protected static Vec4d getDest(Vec4d dest) {
		return dest == null ? new Vec4d() : dest;
	}

	public static Vec4d add(Vec4d left, Vec4d right, Vec4d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x + right.x;
		dest.y = left.y + right.y;
		dest.z = left.z + right.z;
		dest.w = left.w + right.w;

		return dest;
	}

	public static Vec4d sub(Vec4d left, Vec4d right, Vec4d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x - right.x;
		dest.y = left.y - right.y;
		dest.z = left.z - right.z;
		dest.w = left.w - right.w;

		return dest;
	}

	public static Vec4d mul(Vec4d left, Vec4d right, Vec4d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x * right.x;
		dest.y = left.y * right.y;
		dest.z = left.z * right.z;
		dest.w = left.w * right.w;

		return dest;
	}

	public static Vec4d div(Vec4d left, Vec4d right, Vec4d dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x / right.x;
		dest.y = left.y / right.y;
		dest.z = left.z / right.z;
		dest.w = left.w / right.w;

		return dest;
	}

	public static double dot(Vec4d left, Vec4d right) {
		return left.x * right.x + left.y * right.y + left.z * right.z + left.w * right.w;
	}

	public static double angleRad(Vec4d v1, Vec4d v2) {
		double d = dot(v1, v2) / (v1.len() * v2.len());

		if (d < -1f) {
			d = -1f;
		} else if (d > 1.0f) {
			d = 1.0f;
		}

		return Math.acos(d);
	}

	public static double angleDeg(Vec4d v1, Vec4d v2) {
		return Math.toDegrees(angleRad(v1, v2));
	}

	public static Vec4d transform(Mat3 l, Vec4d r, Vec4d dst) {
		dst = getDest(dst);

		double x = l.m00 * r.x + l.m10 * r.y + l.m20 * r.z;
		double y = l.m01 * r.x + l.m11 * r.y + l.m21 * r.z;
		double z = l.m02 * r.x + l.m12 * r.y + l.m22 * r.z;

		dst.x = x;
		dst.y = y;
		dst.z = z;

		return dst;
	}

	public static Vec4d transform(Mat4 l, Vec4d r, Vec4d dst) {
		dst = Vec4d.getDest(dst);

		double x = l.m00 * r.x + l.m10 * r.y + l.m20 * r.z + l.m30 * r.w;
		double y = l.m01 * r.x + l.m11 * r.y + l.m21 * r.z + l.m31 * r.w;
		double z = l.m02 * r.x + l.m12 * r.y + l.m22 * r.z + l.m32 * r.w;
		double w = l.m03 * r.x + l.m13 * r.y + l.m23 * r.z + l.m33 * r.w;

		dst.x = x;
		dst.y = y;
		dst.z = z;
		dst.w = w;

		return dst;
	}

}
