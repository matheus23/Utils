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
public class Vec4 implements FloatBuffable<Vec4> {

	public float x;
	public float y;
	public float z;
	public float w;

	public Vec4() {
		this(0, 0, 0, 0);
	}

	public Vec4(float x, float y, float z, float w) {
		set(x, y, z, w);
	}

	public Vec4 set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	public Vec4 set(Vec4 toCopy) {
		return set(toCopy.x, toCopy.y, toCopy.z, toCopy.w);
	}

	public Vec4 scale(float factor) {
		return scale(factor, factor, factor, factor);
	}

	public Vec4 scale(float xs, float ys, float zs, float ws) {
		this.x *= xs;
		this.y *= ys;
		this.z *= zs;
		this.z *= ws;
		return this;
	}

	public Vec4 translate(float xt, float yt, float zt, float wt) {
		this.x += xt;
		this.y += yt;
		this.z += zt;
		this.z += wt;
		return this;
	}

	public Vec4 negate(Vec4 dest) {
		dest = getDest(dest);

		dest.x = -x;
		dest.y = -y;
		dest.z = -z;
		dest.w = -w;

		return dest;
	}

	public float len() {
		return (float) Math.sqrt(sqLen());
	}

	public float sqLen() {
		return x * x + y * y + z * z + w * w;
	}

	public Vec4 normalize() {
		return scale(len());
	}

	public Vec4 normalize(Vec4 dest) {
		dest = getDest(dest);

		dest.set(this);
		dest.normalize();

		return dest;
	}

	public Vec4 transform(Mat3 mat) {
		return transform(mat, this, this);
	}

	public Vec4 transform(Mat4 mat) {
		return transform(mat, this, this);
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.FloatBuffable#load(java.nio.FloatBuffer)
	 */
	@Override
	public Vec4 load(FloatBuffer buf) {
		this.x = buf.get();
		this.y = buf.get();
		this.y = buf.get();
		this.w = buf.get();
		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.FloatBuffable#store(java.nio.FloatBuffer)
	 */
	@Override
	public Vec4 store(FloatBuffer buf) {
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

	protected static Vec4 getDest(Vec4 dest) {
		return dest == null ? new Vec4() : dest;
	}

	public static Vec4 add(Vec4 left, Vec4 right, Vec4 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x + right.x;
		dest.y = left.y + right.y;
		dest.z = left.z + right.z;
		dest.w = left.w + right.w;

		return dest;
	}

	public static Vec4 sub(Vec4 left, Vec4 right, Vec4 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x - right.x;
		dest.y = left.y - right.y;
		dest.z = left.z - right.z;
		dest.w = left.w - right.w;

		return dest;
	}

	public static Vec4 mul(Vec4 left, Vec4 right, Vec4 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x * right.x;
		dest.y = left.y * right.y;
		dest.z = left.z * right.z;
		dest.w = left.w * right.w;

		return dest;
	}

	public static Vec4 div(Vec4 left, Vec4 right, Vec4 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x / right.x;
		dest.y = left.y / right.y;
		dest.z = left.z / right.z;
		dest.w = left.w / right.w;

		return dest;
	}

	public static float dot(Vec4 left, Vec4 right) {
		return left.x * right.x + left.y * right.y + left.z * right.z + left.w * right.w;
	}

	public static float angleRad(Vec4 v1, Vec4 v2) {
		float d = dot(v1, v2) / (v1.len() * v2.len());

		if (d < -1f) {
			d = -1f;
		} else if (d > 1.0f) {
			d = 1.0f;
		}

		return (float) Math.acos(d);
	}

	public static float angleDeg(Vec4 v1, Vec4 v2) {
		return (float) Math.toDegrees(angleRad(v1, v2));
	}

	public static Vec4 transform(Mat3 l, Vec4 r, Vec4 dst) {
		dst = getDest(dst);

		float x = l.m00 * r.x + l.m10 * r.y + l.m20 * r.z;
		float y = l.m01 * r.x + l.m11 * r.y + l.m21 * r.z;
		float z = l.m02 * r.x + l.m12 * r.y + l.m22 * r.z;

		dst.x = x;
		dst.y = y;
		dst.z = z;

		return dst;
	}

	public static Vec4 transform(Mat4 l, Vec4 r, Vec4 dst) {
		dst = Vec4.getDest(dst);

		float x = l.m00 * r.x + l.m10 * r.y + l.m20 * r.z + l.m30 * r.w;
		float y = l.m01 * r.x + l.m11 * r.y + l.m21 * r.z + l.m31 * r.w;
		float z = l.m02 * r.x + l.m12 * r.y + l.m22 * r.z + l.m32 * r.w;
		float w = l.m03 * r.x + l.m13 * r.y + l.m23 * r.z + l.m33 * r.w;

		dst.x = x;
		dst.y = y;
		dst.z = z;
		dst.w = w;

		return dst;
	}

}
