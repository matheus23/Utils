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

import org.matheusdev.util.Average;
import org.matheusdev.util.NumUtils;
import org.matheusdev.util.vecmath.Mat4;
import org.matheusdev.util.vecmath.Vec2;

/**
 * @author matheusdev
 *
 */
public class Poly implements SATObject {

	protected Vec2 center;
	protected Vec2 centerCached;
	protected final Vec2[] vertices;
	protected final Vec2[] verticesCached;
	protected final Vec2[] normals;
	protected Rect aabb;
	protected Circle circBounds;
	protected Mat4 mat;

	public Poly(Vec2... vertices) {
		final int elements = vertices.length;
		if (elements < 3) {
			throw new IllegalArgumentException("elements < 3: This is not a Polygon.");
		}
		this.vertices = vertices;
		this.verticesCached = new Vec2[elements];
		this.center = calculateCenter();
		this.centerCached = new Vec2();

		this.normals = new Vec2[elements];
		for (int i = 0; i < normals.length; i++) normals[i] = new Vec2();
		this.mat = new Mat4();
		this.aabb = new Rect();
		this.circBounds = createCircularBounds(circBounds);
		updateFromMatrix();
	}

	protected Vec2 calculateCenter() {
		float[] vertX = new float[vertices.length];
		float[] vertY = new float[vertices.length];

		for (int i = 0; i < vertices.length; i++) {
			vertX[i] = vertices[i].x;
			vertY[i] = vertices[i].y;
		}

		return new Vec2(Average.get(vertX), Average.get(vertY));
	}

	public Poly updateFromMatrix() {
		return updateFromMatrix(mat);
	}

	public Poly updateFromMatrix(Mat4 mat) {
		for (int i = 0; i < vertices.length; i++) {
			// if verticesChached[i] is null in the call to Vec2.transform,
			// then a new Vec2 instance will be automatically created.
			verticesCached[i] = Vec2.transform(vertices[i], mat, verticesCached[i]);
		}
		// The same applies to the single center Vec2:
		centerCached = Vec2.transform(center, mat, centerCached);
		// Finally update normals:
		for (int i = 0; i < normals.length; i++) {
			calculateNormal(i);
		}
		return this;
	}

	protected Vec2 calculateNormal(int side) {
		Vec2 v0 = getTransformedVertex(side);
		Vec2 v1 = getTransformedVertex(side + 1);
		return normals[side].set(v1.x - v0.x, v1.y - v0.y).perpLeft().normalize();
	}

	protected Circle createCircularBounds(Circle dest) {
		float maxDist = 0f;

		for (int i = 0; i < vertices.length; i++) {
			float dx = vertices[i].x - center.x;
			float dy = vertices[i].y - center.y;
			maxDist = Math.max(maxDist, (float) Math.sqrt(dx * dx + dy * dy));
		}
		if (dest == null) dest = new Circle(null, 0f);
		dest.set(centerCached, maxDist);
		return dest;
	}

	public Mat4 getMatrix() {
		return mat;
	}

	public Poly setMatrix(Mat4 mat) {
		this.mat = mat;
		return this;
	}

	public int getNumVertices() {
		return vertices.length;
	}

	public Vec2 getOrigin() {
		return center;
	}

	public Vec2 getPosition() {
		return centerCached;
	}

	public Vec2 getVertex(int i) {
		return vertices[NumUtils.clamp(i, vertices.length)];
	}

	public Vec2 getTransformedVertex(int i) {
		return verticesCached[NumUtils.clamp(i, verticesCached.length)];
	}

	public Vec2 getNormal(int side) {
		return normals[NumUtils.clamp(side, normals.length)];
	}

	@Override
	public Vec2[] getTransformedVertices() {
		return verticesCached;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.collision.SATObject#getAxes()
	 */
	@Override
	public Vec2[] getAxes() {
		return normals;
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.collision.SATObject#project(org.matheusdev.util.vecmath.Vec2)
	 */
	@Override
	public Vec2 project(Vec2 axis, Vec2 dest) {
		if (dest == null) dest = new Vec2();

		float min = Vec2.dot(axis, verticesCached[0]);
		float max = min;

		for (int i = 1; i < verticesCached.length; i++) {
			float p = Vec2.dot(axis, verticesCached[i]);

			if (p < min) {
				min = p;
			} else if (p > max) {
				max = p;
			}
		}
		return dest.set(min, max);
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.collision.SATObject#getAABB()
	 */
	@Override
	public Rect getAABB() {
		float minx = verticesCached[0].x;
		float miny = verticesCached[0].y;
		float maxx = verticesCached[0].x;
		float maxy = verticesCached[0].y;

		for (int i = 1; i < verticesCached.length; i++) {
			minx = Math.min(verticesCached[i].x, minx);
			miny = Math.min(verticesCached[i].y, miny);
			maxx = Math.max(verticesCached[i].x, maxx);
			maxy = Math.max(verticesCached[i].y, maxy);
		}
		float w = maxx-minx;
		float h = maxy-miny;
		return aabb.set(minx, miny, w, h);
	}

	/* (non-Javadoc)
	 * @see org.matheusdev.util.collision.Easifyable#getBounds()
	 */
	@Override
	public Circle getBounds() {
		return circBounds.setCenter(centerCached);
	}

}
