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
package org.matheusdev.util.collision;

import org.matheusdev.util.vecmath.Mat4;
import org.matheusdev.util.vecmath.Vec2;

/**
 * @author matheusdev
 *
 */
public class Quad implements SATObject {

	protected Vec2 center;
	protected Vec2 centerCached;

	protected Vec2 topLeft;
	protected Vec2 topRight;
	protected Vec2 botRight;
	protected Vec2 botLeft;

	protected Vec2 topLeftCached;
	protected Vec2 topRightCached;
	protected Vec2 botRightCached;
	protected Vec2 botLeftCached;

	protected Vec2 vertNormal;
	protected Vec2 horizNormal;

	protected Vec2[] verticesCached;
	protected Vec2[] normalsCached;

	protected Rect aabb;

	protected Mat4 mat;

	public Quad(float centerX, float centerY, float w, float h) {
		this(new Vec2(centerX, centerY), w, h);
	}

	public Quad(Vec2 center, float w, float h) {
		this.center = center;

		float hw = w / 2;
		float hh = h / 2;

		this.topLeft = new Vec2(center.x - hw, center.y - hh);
		this.topRight = new Vec2(center.x + hw, center.y - hh);
		this.botRight = new Vec2(center.x + hw, center.y + hh);
		this.botLeft = new Vec2(center.x - hw, center.y + hh);

		this.verticesCached = new Vec2[4];
		this.normalsCached = new Vec2[2];
		this.mat = new Mat4();
		this.aabb = new Rect();
		updateFromMatrix();
	}

	public Quad updateFromMatrix() {
		return updateFromMatrix(mat);
	}

	public Quad updateFromMatrix(Mat4 mat) {
		topLeftCached = Vec2.transform(topLeft, mat, topLeftCached);
		topRightCached = Vec2.transform(topRight, mat, topRightCached);
		botRightCached = Vec2.transform(botRight, mat, botRightCached);
		botLeftCached = Vec2.transform(botLeft, mat, botLeftCached);

		centerCached = Vec2.transform(center, mat, centerCached);

		vertNormal = calculateNormal(topLeft, topRight, vertNormal);
		horizNormal = calculateNormal(topLeft, botLeft, horizNormal);

		return this;
	}

	protected Vec2 calculateNormal(Vec2 v0, Vec2 v1, Vec2 dest) {
		if (dest == null) dest = new Vec2();

		return dest.set(v1.x - v0.x, v1.y - v0.y).perpLeft().normalize();
	}

	public Mat4 getMatrix() {
		return mat;
	}

	public Quad setMatrix(Mat4 mat) {
		this.mat = mat;
		return this;
	}

	public Vec2 getCenter() {
		return centerCached;
	}

	public Vec2[] getTransformedVertices() {
		verticesCached[0] = topLeftCached;
		verticesCached[1] = topRightCached;
		verticesCached[2] = botRightCached;
		verticesCached[3] = botLeftCached;

		return verticesCached;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.collision.SATObject#getAxes()
	 */
	@Override
	public Vec2[] getAxes() {
		normalsCached[0] = vertNormal;
		normalsCached[1] = horizNormal;

		return normalsCached;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.collision.SATObject#project(org.matheusdev.util.vecmath.Vec2)
	 */
	@Override
	public Vec2 project(Vec2 axis) {
		final float p0 = Vec2.dot(axis, topLeftCached);
		final float p1 = Vec2.dot(axis, topRightCached);
		final float p2 = Vec2.dot(axis, botRightCached);
		final float p3 = Vec2.dot(axis, botLeftCached);

		final float min = Math.min(Math.min(Math.min(p0, p1), p2), p3);
		final float max = Math.max(Math.max(Math.max(p0, p1), p2), p3);

		return new Vec2(min, max);
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.collision.SATObject#getAABB()
	 */
	@Override
	public Rect getAABB() {
		float minx = topLeftCached.x;
		float miny = topLeftCached.y;
		float maxx = topLeftCached.x;
		float maxy = topLeftCached.y;

		minx = Math.min(topRightCached.x, minx);
		miny = Math.min(topRightCached.y, miny);
		maxx = Math.max(topRightCached.x, maxx);
		maxy = Math.max(topRightCached.y, maxy);

		minx = Math.min(botRightCached.x, minx);
		miny = Math.min(botRightCached.y, miny);
		maxx = Math.max(botRightCached.x, maxx);
		maxy = Math.max(botRightCached.y, maxy);

		minx = Math.min(botLeftCached.x, minx);
		miny = Math.min(botLeftCached.y, miny);
		maxx = Math.max(botLeftCached.x, maxx);
		maxy = Math.max(botLeftCached.y, maxy);

		float w = maxx-minx;
		float h = maxy-miny;
		return aabb.set(minx, miny, w, h);
	}

}
