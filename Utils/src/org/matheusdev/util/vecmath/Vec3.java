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
public class Vec3 implements FloatBuffable<Vec3> {

	// Pooling:
	private static final Vec3 POOL = new Vec3();
	public static Vec3 getPool() {
		return POOL.set(0, 0, 0);
	}

	public float x;
	public float y;
	public float z;

	public Vec3() {
		this(0, 0, 0);
	}

	public Vec3(float x, float y, float z) {
		set(x, y, z);
	}

	public Vec3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vec3 set(Vec3 toCopy) {
		return set(toCopy.x, toCopy.y, toCopy.z);
	}

	public Vec3 scale(float factor) {
		return scale(factor, factor, factor);
	}

	public Vec3 scale(float xs, float ys, float zs) {
		this.x *= xs;
		this.y *= ys;
		this.z *= zs;
		return this;
	}

	public Vec3 translate(float xt, float yt, float zt) {
		this.x += xt;
		this.y += yt;
		this.z += zt;
		return this;
	}

	public Vec3 negate(Vec3 dest) {
		dest = getDest(dest);

		dest.x = -x;
		dest.y = -y;
		dest.z = -z;

		return dest;
	}

	public float len() {
		return (float) Math.sqrt(sqLen());
	}

	public float sqLen() {
		return x * x + y * y + z * z;
	}

	public Vec3 normalize() {
		return scale(len());
	}

	public Vec3 normalize(Vec3 dest) {
		dest = getDest(dest);

		dest.set(this);
		dest.normalize();

		return dest;
	}

	public Vec3 transform(Mat3 mat) {
		return transform(this, mat, this);
	}

	public Vec3 transform(Mat4 mat) {
		return transform(this, mat, this);
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.FloatBuffable#load(java.nio.FloatBuffer)
	 */
	@Override
	public Vec3 load(FloatBuffer buf) {
		this.x = buf.get();
		this.y = buf.get();
		this.y = buf.get();
		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.FloatBuffable#store(java.nio.FloatBuffer)
	 */
	@Override
	public Vec3 store(FloatBuffer buf) {
		buf.put(x).put(y).put(z);
		return this;
	}

	public int getRGB() {
		int r = ((int) (x * 255)) & 0xFF;
		int g = ((int) (y * 255)) & 0xFF;
		int b = ((int) (z * 255)) & 0xFF;
		return b | (g << 8) | (r << 16) | 0xFF000000;
	}

	public static Vec3 cross(Vec3 left, Vec3 right, Vec3 dest) {
		dest = getDest(dest);

		float x = left.y * right.z - left.z * right.y;
		float y = right.x * left.z - right.z * left.x;
		float z = left.x * right.y - left.y * right.x;

		dest.x = x;
		dest.y = y;
		dest.z = z;

		return dest;
	}

	public static Vec3 add(Vec3 left, Vec3 right, Vec3 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x + right.x;
		dest.y = left.y + right.y;
		dest.z = left.z + right.z;

		return dest;
	}

	public static Vec3 sub(Vec3 left, Vec3 right, Vec3 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x - right.x;
		dest.y = left.y - right.y;
		dest.z = left.z - right.z;

		return dest;
	}

	public static Vec3 mul(Vec3 left, Vec3 right, Vec3 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x * right.x;
		dest.y = left.y * right.y;
		dest.z = left.z * right.z;

		return dest;
	}

	public static Vec3 div(Vec3 left, Vec3 right, Vec3 dest) {
		dest = getDest(dest); // Create new one if needed.

		dest.x = left.x / right.x;
		dest.y = left.y / right.y;
		dest.z = left.z / right.z;

		return dest;
	}

	public static float dot(Vec3 left, Vec3 right) {
		return left.x * right.x + left.y * right.y + left.z * right.z;
	}

	public static float angleRad(Vec3 v1, Vec3 v2) {
		float d = dot(v1, v2) / (v1.len() * v2.len());

		if (d < -1f) {
			d = -1f;
		} else if (d > 1.0f) {
			d = 1.0f;
		}

		return (float) Math.acos(d);
	}

	public static float angleDeg(Vec3 v1, Vec3 v2) {
		return (float) Math.toDegrees(angleRad(v1, v2));
	}

	public static Vec3 transform(Vec3 vec, Mat3 mat, Vec3 dest) {
		dest = getDest(dest);

		Vec4 vec4 = Vec4.getPool().set(vec.x, vec.y, vec.z, 1);
		vec4.transform(mat);
		dest.x = vec4.x / vec4.w;
		dest.y = vec4.y / vec4.w;

		return dest;
	}

	public static Vec3 transform(Vec3 vec, Mat4 mat, Vec3 dest) {
		dest = getDest(dest);

		Vec4 vec4 = Vec4.getPool().set(vec.x, vec.y, vec.z, 1);
		vec4.transform(mat);
		dest.x = vec4.x / vec4.w;
		dest.y = vec4.y / vec4.w;

		return dest;
	}

	protected static Vec3 getDest(Vec3 dest) {
		return dest == null ? new Vec3() : dest;
	}

}
