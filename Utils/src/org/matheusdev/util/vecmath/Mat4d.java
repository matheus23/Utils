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
public class Mat4d implements MatDoubleBuffable<Mat4d> {

	/* Templates:

	 * Normal:

		mat.m00 = tmp; mat.m01 = tmp; mat.m02 = tmp; mat.m03 = tmp;
		mat.m10 = tmp; mat.m11 = tmp; mat.m12 = tmp; mat.m13 = tmp;
		mat.m20 = tmp; mat.m21 = tmp; mat.m22 = tmp; mat.m23 = tmp;
		mat.m30 = tmp; mat.m31 = tmp; mat.m32 = tmp; mat.m33 = tmp;

	 * Transposed:

		mat.m00 = tmp; mat.m10 = tmp; mat.m20 = tmp; mat.m30 = tmp;
		mat.m01 = tmp; mat.m11 = tmp; mat.m21 = tmp; mat.m31 = tmp;
		mat.m02 = tmp; mat.m12 = tmp; mat.m22 = tmp; mat.m32 = tmp;
		mat.m03 = tmp; mat.m13 = tmp; mat.m23 = tmp; mat.m33 = tmp;

	 */

	public double m00, m01, m02, m03;
	public double m10, m11, m12, m13;
	public double m20, m21, m22, m23;
	public double m30, m31, m32, m33;

	public Mat4d() {
		identity();
	}

	public Mat4d(Mat4d source) {
		set(source);
	}

	public Mat4d set(Mat4d source) {
		m00 = source.m00; m01 = source.m01; m02 = source.m02; m03 = source.m03;
		m10 = source.m10; m11 = source.m11; m12 = source.m12; m13 = source.m13;
		m20 = source.m20; m21 = source.m21; m22 = source.m22; m23 = source.m23;
		m30 = source.m30; m31 = source.m31; m32 = source.m32; m33 = source.m33;

		return this;
	}

	public Mat4d set(
			double m00, double m01, double m02, double m03,
			double m10, double m11, double m12, double m13,
			double m20, double m21, double m22, double m23,
			double m30, double m31, double m32, double m33) {
		this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
		this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
		this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
		this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;

		return this;
	}

	public Mat4d identity() {
		return set(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1);
	}

	public Mat4d zero() {
		return set(
				0, 0, 0, 0,
				0, 0, 0, 0,
				0, 0, 0, 0,
				0, 0, 0, 0);
	}

	public Mat4d transpose() {
		double /*  no need  */ m01 = this.m10, m02 = this.m20, m03 = this.m30;
		double m10 = this.m01, /*  no need  */ m12 = this.m21, m13 = this.m31;
		double m20 = this.m02, m21 = this.m12, /*  no need  */ m23 = this.m32;
		double m30 = this.m03, m31 = this.m13, m32 = this.m23  /*  no need */;

		/*  no need  */ this.m01 = m01; this.m02 = m02; this.m03 = m03;
		this.m10 = m10; /*  no need  */ this.m12 = m12; this.m13 = m13;
		this.m20 = m20; this.m21 = m21; /*  no need  */ this.m23 = m23;
		this.m30 = m30; this.m31 = m31; this.m32 = m32; /*  no need */;

		return this;
	}

	public Mat4d translate(Vec2d vec) {
		return translate(this, vec, this);
	}

	public Mat4d translate(Vec3d vec) {
		return translate(this, vec, this);
	}

	public Mat4d rotate(double alpha, Vec3d axis, Mat4d dest) {
		return rotate(alpha, axis, this, dest);
	}

	public Mat4d rotate(double alpha, Vec3d axis) {
		return rotate(alpha, axis, this, this);
	}

	public double determinant() {
		return m00
			* ((m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32)
				- m13 * m22 * m31
				- m11 * m23 * m32
				- m12 * m21 * m33) - m01
			* ((m10 * m22 * m33 + m12 * m23 * m30 + m13 * m20 * m32)
				- m13 * m22 * m30
				- m10 * m23 * m32
				- m12 * m20 * m33) + m02
			* ((m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31)
				- m13 * m21 * m30
				- m10 * m23 * m31
				- m11 * m20 * m33) - m03
			* ((m10 * m21 * m32 + m11 * m22 * m30 + m12 * m20 * m31)
				- m12 * m21 * m30
				- m10 * m22 * m31
				- m11 * m20 * m32);
	}

	public Mat4d inv() {
		return inv(this, this);
	}

	public Mat4d negate() {
		return negate(this, this);
	}

	public Mat3d asMat3() {
		return new Mat3d().set(
				m00, m01, m02,
				m10, m11, m12,
				m20, m21, m22);
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.doubleBuffable#load(java.nio.doubleBuffer)
	 */
	@Override
	public Mat4d load(DoubleBuffer buf) {
		m00 = buf.get(); m01 = buf.get(); m02 = buf.get(); m03 = buf.get();
		m10 = buf.get(); m11 = buf.get(); m12 = buf.get(); m13 = buf.get();
		m20 = buf.get(); m21 = buf.get(); m22 = buf.get(); m23 = buf.get();
		m30 = buf.get(); m31 = buf.get(); m32 = buf.get(); m33 = buf.get();

		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.doubleBuffable#store(java.nio.doubleBuffer)
	 */
	@Override
	public Mat4d store(DoubleBuffer buf) {
		buf.put(m00).put(m01).put(m02).put(m03);
		buf.put(m10).put(m11).put(m12).put(m13);
		buf.put(m20).put(m21).put(m22).put(m23);
		buf.put(m30).put(m31).put(m32).put(m33);

		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.MatdoubleBuffable#loadTransposed(java.nio.doubleBuffer)
	 */
	@Override
	public Mat4d loadTransposed(DoubleBuffer buf) {
		m00 = buf.get(); m10 = buf.get(); m20 = buf.get(); m30 = buf.get();
		m01 = buf.get(); m11 = buf.get(); m21 = buf.get(); m31 = buf.get();
		m02 = buf.get(); m12 = buf.get(); m22 = buf.get(); m32 = buf.get();
		m03 = buf.get(); m13 = buf.get(); m23 = buf.get(); m33 = buf.get();

		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.MatdoubleBuffable#storeTransposed(java.nio.doubleBuffer)
	 */
	@Override
	public Mat4d storeTransposed(DoubleBuffer buf) {
		buf.put(m00).put(m10).put(m20).put(m30);
		buf.put(m01).put(m11).put(m21).put(m31);
		buf.put(m02).put(m12).put(m22).put(m32);
		buf.put(m03).put(m13).put(m23).put(m33);

		return this;
	}

	public static Mat4d getDest(Mat4d dest) {
		return dest == null ? new Mat4d() : dest;
	}

	public static Mat4d translate(Mat4d mat, Vec2d vec, Mat4d dest) {
		dest = getDest(dest);

		dest.m30 += mat.m00 * vec.x + mat.m10 * vec.y;
		dest.m31 += mat.m01 * vec.x + mat.m11 * vec.y;
		dest.m32 += mat.m02 * vec.x + mat.m12 * vec.y;
		dest.m33 += mat.m03 * vec.x + mat.m13 * vec.y;

		return dest;
	}

	public static Mat4d translate(Mat4d mat, Vec3d vec, Mat4d dest) {
		dest = getDest(dest);

		dest.m30 += mat.m00 * vec.x + mat.m10 * vec.y + mat.m20 * vec.z;
		dest.m31 += mat.m01 * vec.x + mat.m11 * vec.y + mat.m21 * vec.z;
		dest.m32 += mat.m02 * vec.x + mat.m12 * vec.y + mat.m22 * vec.z;
		dest.m33 += mat.m03 * vec.x + mat.m13 * vec.y + mat.m23 * vec.z;

		return dest;
	}

	public static Mat4d scale(Mat4d src, Vec3d vec, Mat4d dest) {
		dest = getDest(dest);

		dest.m00 = src.m00 * vec.x; dest.m01 = src.m01 * vec.y; dest.m02 = src.m02 * vec.z;
		dest.m10 = src.m10 * vec.x; dest.m11 = src.m11 * vec.y; dest.m12 = src.m12 * vec.z;
		dest.m20 = src.m20 * vec.x; dest.m21 = src.m21 * vec.y; dest.m22 = src.m22 * vec.z;
		dest.m30 = src.m30 * vec.x; dest.m31 = src.m31 * vec.y; dest.m32 = src.m32 * vec.z;

		return dest;
	}

	public static Mat4d add(Mat4d l, Mat4d r, Mat4d dst) {
		dst = getDest(dst);

		dst.m00 = l.m00 + r.m00; dst.m01 = l.m01 + r.m01; dst.m02 = l.m02 + r.m02; dst.m03 = l.m03 + r.m03;
		dst.m10 = l.m10 + r.m10; dst.m11 = l.m11 + r.m11; dst.m12 = l.m12 + r.m12; dst.m13 = l.m13 + r.m13;
		dst.m20 = l.m20 + r.m20; dst.m21 = l.m21 + r.m21; dst.m22 = l.m22 + r.m22; dst.m23 = l.m23 + r.m23;
		dst.m30 = l.m30 + r.m30; dst.m31 = l.m31 + r.m31; dst.m32 = l.m32 + r.m32; dst.m33 = l.m33 + r.m33;

		return dst;
	}

	public static Mat4d sub(Mat4d l, Mat4d r, Mat4d dst) {
		dst = getDest(dst);

		dst.m00 = l.m00 - r.m00; dst.m01 = l.m01 - r.m01; dst.m02 = l.m02 - r.m02; dst.m03 = l.m03 - r.m03;
		dst.m10 = l.m10 - r.m10; dst.m11 = l.m11 - r.m11; dst.m12 = l.m12 - r.m12; dst.m13 = l.m13 - r.m13;
		dst.m20 = l.m20 - r.m20; dst.m21 = l.m21 - r.m21; dst.m22 = l.m22 - r.m22; dst.m23 = l.m23 - r.m23;
		dst.m30 = l.m30 - r.m30; dst.m31 = l.m31 - r.m31; dst.m32 = l.m32 - r.m32; dst.m33 = l.m33 - r.m33;

		return dst;
	}

	public static Mat4d mul(Mat4d l, Mat4d r, Mat4d dst) {
		dst = getDest(dst);

		double m00 = l.m00 * r.m00 + l.m10 * r.m01 + l.m20 * r.m02 + l.m30 * r.m03;
		double m01 = l.m01 * r.m00 + l.m11 * r.m01 + l.m21 * r.m02 + l.m31 * r.m03;
		double m02 = l.m02 * r.m00 + l.m12 * r.m01 + l.m22 * r.m02 + l.m32 * r.m03;
		double m03 = l.m03 * r.m00 + l.m13 * r.m01 + l.m23 * r.m02 + l.m33 * r.m03;
		double m10 = l.m00 * r.m10 + l.m10 * r.m11 + l.m20 * r.m12 + l.m30 * r.m13;
		double m11 = l.m01 * r.m10 + l.m11 * r.m11 + l.m21 * r.m12 + l.m31 * r.m13;
		double m12 = l.m02 * r.m10 + l.m12 * r.m11 + l.m22 * r.m12 + l.m32 * r.m13;
		double m13 = l.m03 * r.m10 + l.m13 * r.m11 + l.m23 * r.m12 + l.m33 * r.m13;
		double m20 = l.m00 * r.m20 + l.m10 * r.m21 + l.m20 * r.m22 + l.m30 * r.m23;
		double m21 = l.m01 * r.m20 + l.m11 * r.m21 + l.m21 * r.m22 + l.m31 * r.m23;
		double m22 = l.m02 * r.m20 + l.m12 * r.m21 + l.m22 * r.m22 + l.m32 * r.m23;
		double m23 = l.m03 * r.m20 + l.m13 * r.m21 + l.m23 * r.m22 + l.m33 * r.m23;
		double m30 = l.m00 * r.m30 + l.m10 * r.m31 + l.m20 * r.m32 + l.m30 * r.m33;
		double m31 = l.m01 * r.m30 + l.m11 * r.m31 + l.m21 * r.m32 + l.m31 * r.m33;
		double m32 = l.m02 * r.m30 + l.m12 * r.m31 + l.m22 * r.m32 + l.m32 * r.m33;
		double m33 = l.m03 * r.m30 + l.m13 * r.m31 + l.m23 * r.m32 + l.m33 * r.m33;

		dst.m00 = m00;
		dst.m01 = m01;
		dst.m02 = m02;
		dst.m03 = m03;
		dst.m10 = m10;
		dst.m11 = m11;
		dst.m12 = m12;
		dst.m13 = m13;
		dst.m20 = m20;
		dst.m21 = m21;
		dst.m22 = m22;
		dst.m23 = m23;
		dst.m30 = m30;
		dst.m31 = m31;
		dst.m32 = m32;
		dst.m33 = m33;

		return dst;
	}

	public static Mat4d rotate(double alpha, Vec3d axis, Mat4d src, Mat4d dest) {
		dest = getDest(dest);

		double cos = Math.cos(alpha);
		double sin = Math.sin(alpha);
		double nivCos = 1.0f - cos;
		double xy = axis.x*axis.y;
		double yz = axis.y*axis.z;
		double xz = axis.x*axis.z;
		double xs = axis.x*sin;
		double ys = axis.y*sin;
		double zs = axis.z*sin;

		double f00 = axis.x*axis.x*nivCos+cos;
		double f01 = xy*nivCos+zs;
		double f02 = xz*nivCos-ys;
		double f10 = xy*nivCos-zs;
		double f11 = axis.y*axis.y*nivCos+cos;
		double f12 = yz*nivCos+xs;
		double f20 = xz*nivCos+ys;
		double f21 = yz*nivCos-xs;
		double f22 = axis.z*axis.z*nivCos+cos;

		double t00 = src.m00 * f00 + src.m10 * f01 + src.m20 * f02;
		double t01 = src.m01 * f00 + src.m11 * f01 + src.m21 * f02;
		double t02 = src.m02 * f00 + src.m12 * f01 + src.m22 * f02;
		double t03 = src.m03 * f00 + src.m13 * f01 + src.m23 * f02;
		double t10 = src.m00 * f10 + src.m10 * f11 + src.m20 * f12;
		double t11 = src.m01 * f10 + src.m11 * f11 + src.m21 * f12;
		double t12 = src.m02 * f10 + src.m12 * f11 + src.m22 * f12;
		double t13 = src.m03 * f10 + src.m13 * f11 + src.m23 * f12;

		dest.m20 = src.m00 * f20 + src.m10 * f21 + src.m20 * f22;
		dest.m21 = src.m01 * f20 + src.m11 * f21 + src.m21 * f22;
		dest.m22 = src.m02 * f20 + src.m12 * f21 + src.m22 * f22;
		dest.m23 = src.m03 * f20 + src.m13 * f21 + src.m23 * f22;
		dest.m00 = t00;
		dest.m01 = t01;
		dest.m02 = t02;
		dest.m03 = t03;
		dest.m10 = t10;
		dest.m11 = t11;
		dest.m12 = t12;
		dest.m13 = t13;

		return dest;
	}

	private static double determ3x3(double t00, double t01, double t02,
		     double t10, double t11, double t12,
		     double t20, double t21, double t22) {
		return  t00 * (t11 * t22 - t12 * t21)
		      + t01 * (t12 * t20 - t10 * t22)
		      + t02 * (t10 * t21 - t11 * t20);
	}

	public static Mat4d inv(Mat4d src, Mat4d dest) {
		dest = getDest(dest);

		double determ = src.determinant();

		if (determ == 0f) {
			throw new IllegalStateException("Error while inverting: determinant == 0f");
		}
		double invDeterm = 1f / determ;

		// first row
		double t00 =  determ3x3(src.m11, src.m12, src.m13, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
		double t01 = -determ3x3(src.m10, src.m12, src.m13, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
		double t02 =  determ3x3(src.m10, src.m11, src.m13, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
		double t03 = -determ3x3(src.m10, src.m11, src.m12, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
		// second row
		double t10 = -determ3x3(src.m01, src.m02, src.m03, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
		double t11 =  determ3x3(src.m00, src.m02, src.m03, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
		double t12 = -determ3x3(src.m00, src.m01, src.m03, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
		double t13 =  determ3x3(src.m00, src.m01, src.m02, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
		// third row
		double t20 =  determ3x3(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m31, src.m32, src.m33);
		double t21 = -determ3x3(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m30, src.m32, src.m33);
		double t22 =  determ3x3(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m30, src.m31, src.m33);
		double t23 = -determ3x3(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m30, src.m31, src.m32);
		// fourth row
		double t30 = -determ3x3(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m21, src.m22, src.m23);
		double t31 =  determ3x3(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m20, src.m22, src.m23);
		double t32 = -determ3x3(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m20, src.m21, src.m23);
		double t33 =  determ3x3(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m20, src.m21, src.m22);

		// transpose and divide by the determinant
		dest.m00 = t00 * invDeterm;
		dest.m11 = t11 * invDeterm;
		dest.m22 = t22 * invDeterm;
		dest.m33 = t33 * invDeterm;
		dest.m01 = t10 * invDeterm;
		dest.m10 = t01 * invDeterm;
		dest.m20 = t02 * invDeterm;
		dest.m02 = t20 * invDeterm;
		dest.m12 = t21 * invDeterm;
		dest.m21 = t12 * invDeterm;
		dest.m03 = t30 * invDeterm;
		dest.m30 = t03 * invDeterm;
		dest.m13 = t31 * invDeterm;
		dest.m31 = t13 * invDeterm;
		dest.m32 = t23 * invDeterm;
		dest.m23 = t32 * invDeterm;
		return dest;
	}

	public static Mat4d negate(Mat4d src, Mat4d dest) {
		dest = getDest(dest);

		dest.m00 = -src.m00; dest.m01 = -src.m01; dest.m02 = -src.m02; dest.m03 = -src.m03;
		dest.m10 = -src.m10; dest.m11 = -src.m11; dest.m12 = -src.m12; dest.m13 = -src.m13;
		dest.m20 = -src.m20; dest.m21 = -src.m21; dest.m22 = -src.m22; dest.m23 = -src.m23;
		dest.m30 = -src.m30; dest.m31 = -src.m31; dest.m32 = -src.m32; dest.m33 = -src.m33;

		return dest;
	}

}
