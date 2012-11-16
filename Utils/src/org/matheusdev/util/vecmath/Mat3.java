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
public class Mat3 implements MatFloatBuffable<Mat3> {

	public float m00, m01, m02;
	public float m10, m11, m12;
	public float m20, m21, m22;

	public Mat3() {
		identity();
	}

	public Mat3(Mat3 source) {
		set(source);
	}

	public Mat3 set(Mat3 source) {
		m00 = source.m00; m01 = source.m01; m02 = source.m02;
		m10 = source.m10; m11 = source.m11; m12 = source.m12;
		m20 = source.m20; m21 = source.m21; m22 = source.m22;

		return this;
	}

	public Mat3 set(
			float m00, float m01, float m02,
			float m10, float m11, float m12,
			float m20, float m21, float m22) {
		this.m00 = m00; this.m01 = m01; this.m02 = m02;
		this.m10 = m10; this.m11 = m11; this.m12 = m12;
		this.m20 = m20; this.m21 = m21; this.m22 = m22;

		return this;
	}

	public Mat3 identity() {
		m00 = 1; m01 = 0; m02 = 0;
		m10 = 0; m11 = 1; m12 = 0;
		m20 = 0; m21 = 0; m22 = 1;

		return this;
	}

	public Mat3 zero() {
		m00 = 0; m01 = 0; m02 = 0;
		m10 = 0; m11 = 0; m12 = 0;
		m20 = 0; m21 = 0; m22 = 0;

		return this;
	}

	public Mat3 transpose() {
		float /*  no need  */ m01 = this.m10, m02 = this.m20;
		float m10 = this.m01, /*  no need  */ m12 = this.m21;
		float m20 = this.m02, m21 = this.m12; /*  no need  */

		/*  no need  */ this.m01 = m01; this.m02 = m02;
		this.m10 = m10; /*  no need  */ this.m12 = m12;
		this.m20 = m20; this.m21 = m21; /*  no need  */

		return this;
	}

	public float determinant() {
		return    m00 * (m11 * m22 - m12 * m21)
				+ m01 * (m12 * m20 - m10 * m22)
				+ m02 * (m10 * m21 - m11 * m20);
	}

	public Mat3 inv() {
		return inv(this, this);
	}

	public Mat3 negate() {
		return negate(this, this);
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.FloatBuffable#load(java.nio.FloatBuffer)
	 */
	@Override
	public Mat3 load(FloatBuffer buf) {
		m00 = buf.get(); m01 = buf.get(); m02 = buf.get();
		m10 = buf.get(); m11 = buf.get(); m12 = buf.get();
		m20 = buf.get(); m21 = buf.get(); m22 = buf.get();

		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.FloatBuffable#store(java.nio.FloatBuffer)
	 */
	@Override
	public Mat3 store(FloatBuffer buf) {
		buf.put(m00).put(m01).put(m02);
		buf.put(m10).put(m11).put(m12);
		buf.put(m20).put(m21).put(m22);

		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.MatFloatBuffable#loadTransposed(java.nio.FloatBuffer)
	 */
	@Override
	public Mat3 loadTransposed(FloatBuffer buf) {
		m00 = buf.get(); m10 = buf.get(); m20 = buf.get();;
		m01 = buf.get(); m11 = buf.get(); m21 = buf.get();
		m02 = buf.get(); m12 = buf.get(); m22 = buf.get();

		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.MatFloatBuffable#storeTransposed(java.nio.FloatBuffer)
	 */
	@Override
	public Mat3 storeTransposed(FloatBuffer buf) {
		buf.put(m00).put(m10).put(m20);
		buf.put(m01).put(m11).put(m21);
		buf.put(m02).put(m12).put(m22);

		return this;
	}

	public static Mat3 getDest(Mat3 dest) {
		return dest == null ? new Mat3() : dest;
	}

	public static Mat3 scale(Mat3 src, Vec3 vec, Mat3 dest) {
		dest = getDest(dest);

		dest.m00 = src.m00 * vec.x; dest.m01 = src.m01 * vec.y; dest.m02 = src.m02 * vec.z;
		dest.m10 = src.m10 * vec.x; dest.m11 = src.m11 * vec.y; dest.m12 = src.m12 * vec.z;
		dest.m20 = src.m20 * vec.x; dest.m21 = src.m21 * vec.y; dest.m22 = src.m22 * vec.z;

		return dest;
	}

	public static Mat3 add(Mat3 l, Mat3 r, Mat3 dst) {
		dst = getDest(dst);

		dst.m00 = l.m00 + r.m00; dst.m01 = l.m01 + r.m01; dst.m02 = l.m02 + r.m02;
		dst.m10 = l.m10 + r.m10; dst.m11 = l.m11 + r.m11; dst.m12 = l.m12 + r.m12;
		dst.m20 = l.m20 + r.m20; dst.m21 = l.m21 + r.m21; dst.m22 = l.m22 + r.m22;

		return dst;
	}

	public static Mat3 sub(Mat3 l, Mat3 r, Mat3 dst) {
		dst = getDest(dst);

		dst.m00 = l.m00 - r.m00; dst.m01 = l.m01 - r.m01; dst.m02 = l.m02 - r.m02;
		dst.m10 = l.m10 - r.m10; dst.m11 = l.m11 - r.m11; dst.m12 = l.m12 - r.m12;
		dst.m20 = l.m20 - r.m20; dst.m21 = l.m21 - r.m21; dst.m22 = l.m22 - r.m22;

		return dst;
	}

	public static Mat3 mul(Mat3 l, Mat3 r, Mat3 dst) {
		dst = getDest(dst);

		float m00 = l.m00 * r.m00 + l.m10 * r.m01 + l.m20 * r.m02;
		float m01 =	l.m01 * r.m00 + l.m11 * r.m01 + l.m21 * r.m02;
		float m02 =	l.m02 * r.m00 + l.m12 * r.m01 + l.m22 * r.m02;
		float m10 =	l.m00 * r.m10 + l.m10 * r.m11 + l.m20 * r.m12;
		float m11 =	l.m01 * r.m10 + l.m11 * r.m11 + l.m21 * r.m12;
		float m12 =	l.m02 * r.m10 + l.m12 * r.m11 + l.m22 * r.m12;
		float m20 =	l.m00 * r.m20 + l.m10 * r.m21 + l.m20 * r.m22;
		float m21 =	l.m01 * r.m20 + l.m11 * r.m21 + l.m21 * r.m22;
		float m22 =	l.m02 * r.m20 + l.m12 * r.m21 + l.m22 * r.m22;

		dst.m00 = m00;
		dst.m01 = m01;
		dst.m02 = m02;
		dst.m10 = m10;
		dst.m11 = m11;
		dst.m12 = m12;
		dst.m20 = m20;
		dst.m21 = m21;
		dst.m22 = m22;

		return dst;
	}

	public static Mat3 inv(Mat3 src, Mat3 dest) {
		dest = getDest(dest);

		float determ = src.determinant();

		if (determ == 0f) {
			throw new IllegalStateException("Error while inverting: determinant == 0f");
		}
		float invDeterm = 1f / determ;

		float t00 =  src.m11 * src.m22 - src.m12 * src.m21;
		float t01 = -src.m10 * src.m22 + src.m12 * src.m20;
		float t02 =  src.m10 * src.m21 - src.m11 * src.m20;
		float t10 = -src.m01 * src.m22 + src.m02 * src.m21;
		float t11 =  src.m00 * src.m22 - src.m02 * src.m20;
		float t12 = -src.m00 * src.m21 + src.m01 * src.m20;
		float t20 =  src.m01 * src.m12 - src.m02 * src.m11;
		float t21 = -src.m00 * src.m12 + src.m02 * src.m10;
		float t22 =  src.m00 * src.m11 - src.m01 * src.m10;

		dest.m00 = t00 * invDeterm;
		dest.m11 = t11 * invDeterm;
		dest.m22 = t22 * invDeterm;
		dest.m01 = t10 * invDeterm;
		dest.m10 = t01 * invDeterm;
		dest.m20 = t02 * invDeterm;
		dest.m02 = t20 * invDeterm;
		dest.m12 = t21 * invDeterm;
		dest.m21 = t12 * invDeterm;
		return dest;
	}

	public static Mat3 negate(Mat3 src, Mat3 dest) {
		dest = getDest(dest);

		dest.m00 = -src.m00; dest.m01 = -src.m01; dest.m02 = -src.m02;
		dest.m10 = -src.m10; dest.m11 = -src.m11; dest.m12 = -src.m12;
		dest.m20 = -src.m20; dest.m21 = -src.m21; dest.m22 = -src.m22;

		return dest;
	}

}
