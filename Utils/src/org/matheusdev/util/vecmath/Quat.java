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
public class Quat implements FloatBuffable<Quat> {

	public float x;
	public float y;
	public float z;
	public float w;

	public Quat() {
		identity();
	}

	public Quat(float x, float y, float z, float w) {
		set(x, y, z, w);
	}

	public Quat(Vec4 vec) {
		setFromVec4(vec);
	}

	public Quat(Vec3 vec, float alpha) {
		setFromVec3(vec, alpha);
	}

	public Quat identity() {
		return set(0, 0, 0, 1);
	}

	public Quat set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;

		return this;
	}

	public float sqLen() {
		return x * x + y * y + z * z + w * w;
	}

	public float len() {
		return (float) Math.sqrt(sqLen());
	}

	public Quat normalize() {
		return normalize(this, this);
	}

	public float dot(Quat left, Quat right) {
		return left.x * right.x + left.y * right.y + left.z * right.z + left.w * right.w;
	}

	public Quat negate() {
		return negate(this, this);
	}

	public Quat scale(float factor) {
		return scale(factor, this, this);
	}

	public void setFromVec4(Vec4 vec) {
		x = vec.x;
		y = vec.y;
		z = vec.z;

		float n = (float) Math.sqrt(x * x + y * y + z * z);
		float s = (float) (Math.sin(vec.w / 2) / n);

		x *= s;
		y *= s;
		z *= s;
		w = (float) Math.cos(vec.w / 2);
	}

	public void setFromVec3(Vec3 vec, float alpha) {
		x = vec.x;
		y = vec.y;
		z = vec.z;

		float hAlpha = alpha / 2;
		float n = vec.len();
		float s = (float) (Math.sin(hAlpha) / n);

		x *= s;
		y *= s;
		z *= s;
		w = (float) Math.cos(hAlpha);
	}

	public Quat setFromMat(Mat3 mat) {
		float s;
		float tr = mat.m00 + mat.m11 + mat.m22;
		if (tr >= 0.0) {
			s = (float) Math.sqrt(tr + 1.0);
			w = s * 0.5f;
			s = 0.5f / s;
			x = (mat.m21 - mat.m12) * s;
			y = (mat.m02 - mat.m20) * s;
			z = (mat.m10 - mat.m01) * s;
		} else {
			float max = Math.max(Math.max(mat.m00, mat.m11), mat.m22);
			if (max == mat.m00) {
				s = (float) Math.sqrt(mat.m00 - (mat.m11 + mat.m22) + 1.0);
				x = s * 0.5f;
				s = 0.5f / s;
				y = (mat.m01 + mat.m10) * s;
				z = (mat.m20 + mat.m02) * s;
				w = (mat.m21 - mat.m12) * s;
			} else if (max == mat.m11) {
				s = (float) Math.sqrt(mat.m11 - (mat.m22 + mat.m00) + 1.0);
				y = s * 0.5f;
				s = 0.5f / s;
				z = (mat.m12 + mat.m21) * s;
				x = (mat.m01 + mat.m10) * s;
				w = (mat.m02 - mat.m20) * s;
			} else {
				s = (float) Math.sqrt(mat.m22 - (mat.m00 + mat.m11) + 1.0);
				z = s * 0.5f;
				s = 0.5f / s;
				x = (mat.m20 + mat.m02) * s;
				y = (mat.m12 + mat.m21) * s;
				w = (mat.m10 - mat.m01) * s;
			}
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.FloatBuffable#load(java.nio.FloatBuffer)
	 */
	@Override
	public Quat load(FloatBuffer buf) {
		this.x = buf.get();
		this.y = buf.get();
		this.z = buf.get();
		this.w = buf.get();
		return this;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.vecmath.FloatBuffable#store(java.nio.FloatBuffer)
	 */
	@Override
	public Quat store(FloatBuffer buf) {
		buf.put(x).put(y).put(z).put(w);
		return this;
	}

	public static Quat getDest(Quat dest) {
		return dest == null ? new Quat() : dest;
	}

	public static Quat normalize(Quat quat, Quat dest) {
		dest = getDest(dest);

		float invLen = 1f / dest.len();

		dest.set(dest.x * invLen, dest.y * invLen, dest.z * invLen, dest.w * invLen);

		return dest;
	}

	public static Quat negate(Quat quat, Quat dest) {
		dest = getDest(dest);

		dest.x = -quat.x;
		dest.y = -quat.y;
		dest.z = -quat.z;
		dest.w = quat.w;

		return dest;
	}

	public static Quat scale(float factor, Quat quat, Quat dest) {
		dest = getDest(dest);

		dest.x = quat.x * factor;
		dest.y = quat.y * factor;
		dest.z = quat.z * factor;
		dest.w = quat.w * factor;

		return dest;
	}

	public static Quat mul(Quat l, Quat r, Quat dest) {
		dest = getDest(dest);

		dest.set(l.x * r.w + l.w * r.x + l.y * r.z - l.z * r.y,
				 l.y * r.w + l.w * r.y + l.z * r.x - l.x * r.z,
				 l.z * r.w + l.w * r.z + l.x * r.y - l.y * r.x,
				 l.w * r.w - l.x * r.x - l.y * r.y - l.z * r.z);

		return dest;
	}

	public static Quat mulInv(Quat l, Quat r, Quat dest) {
		dest = getDest(dest);

		float n = r.sqLen();

		n = (n == 0f ? n : 1 / n);

		return dest.set(
				(l.x * r.w - l.w * r.x - l.y * r.z + l.z * r.y) * n,
				(l.y * r.w - l.w * r.y - l.z * r.x + l.x * r.z) * n,
				(l.z * r.w - l.w * r.z - l.x * r.y + l.y * r.x) * n,
				(l.w * r.w + l.x * r.x + l.y * r.y + l.z * r.z) * n);
	}

}
