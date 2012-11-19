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

import org.matheusdev.util.vecmath.Vec2;


/**
 * @author matheusdev
 *
 */
public final class Collision {

	public static long axesTested = 0;
	public static long objectsTested = 0;

	private Collision() {
	}

	public static boolean rectVsRect(Rect r1, Rect r2) {
		// Sperating axis theorem simplified for AABBes.
		if ((r1.x >= (r2.x + r2.w)) || ((r1.x + r1.w) <= r2.x)) {
			return false;
		}
		if ((r1.y >= (r2.y + r2.h)) || ((r1.y + r1.h) <= r2.y)) {
			return false;
		}
		return true;
	}

	public static boolean circleVsCircle(Circle c1, Circle c2) {
		float dx = c2.getCenter().x - c1.getCenter().x;
		float dy = c2.getCenter().y - c1.getCenter().y;
		float radii = c1.getRadius() + c2.getRadius();
		//      Squared Distance   squared "projections" of the circles
		return (dx * dx + dy * dy) < (radii * radii);
	}

	public static boolean rectContainsVec(Rect r, Vec2 vec) {
		return (vec.x >= r.x && vec.y >= r.y && vec.x < r.right() && vec.y < r.bottom());
	}

	public static boolean lineVsPoly(Vec2 start, Vec2 end, SATObject obj) {
		Vec2 normal = new Vec2(end.x-start.x, end.y-start.y);

		Vec2 proj0 = obj.project(normal);
		Vec2 proj1 = new Vec2(Vec2.dot(start, normal), Vec2.dot(end, normal));

		return !noProjOverlap(proj0, proj1);
	}

	public static Vec2 polyVsPolyMTV(SATObject obj0, SATObject obj1) {
		if (circleVsCircle(obj0.getBounds(), obj1.getBounds())) {
			objectsTested++;
			float overlap = Float.MAX_VALUE;
			Vec2 smallest = null;

			Vec2[] axes0 = obj0.getAxes();
			Vec2[] axes1 = obj1.getAxes();

			for (Vec2 axis : axes0) {
				axesTested++;
				Vec2 proj0 = obj0.project(axis);
				Vec2 proj1 = obj1.project(axis);

				if (noProjOverlap(proj0, proj1)) {
					return null;
				} else {
					float o = projOverlap(proj0, proj1);

					if (o < overlap) {
						overlap = o;
						smallest = axis;
					}
				}
			}

			for (Vec2 axis : axes1) {
				axesTested++;
				Vec2 proj0 = obj0.project(axis);
				Vec2 proj1 = obj1.project(axis);

				if (noProjOverlap(proj0, proj1)) {
					return null;
				} else {
					float o = projOverlap(proj0, proj1);

					if (o < overlap) {
						overlap = o;
						smallest = axis;
					}
				}
			}
			Vec2 mtv = new Vec2(smallest.x * overlap, smallest.y * overlap);
			System.out.println(overlap);
			return mtv;
		} else {
			return null;
		}
	}

	public static boolean polyVsCircle(SATObject obj, Circle circ) {
		if (circleVsCircle(obj.getBounds(), circ)) {
			objectsTested++;
			Vec2[] vertices = obj.getTransformedVertices();
			Vec2[] axes = new Vec2[vertices.length];

			for (int i = 0; i < axes.length; i++) {
				 axes[i] = new Vec2(circ.getCenter().x - vertices[i].x, circ.getCenter().y - vertices[i].y).normalize();
			}
			return seperatingAxesTest(axes, obj, circ);
		} else {
			return false;
		}
	}

	public static boolean polyVsPoly(SATObject obj0, SATObject obj1) {
		if (circleVsCircle(obj0.getBounds(), obj1.getBounds())) {
			objectsTested++;
			Vec2[] axes0 = obj0.getAxes();
			Vec2[] axes1 = obj1.getAxes();

			return seperatingAxesTest(axes0, axes1, obj0, obj1);
		} else {
			return false;
		}
	}

	public static boolean seperatingAxesTest(Vec2[] axes0, Vec2[] axes1, Projectable p0, Projectable p1) {
		if (!seperatingAxesTest(axes0, p0, p1)) {
			return false;
		}
		if (!seperatingAxesTest(axes1, p0, p1)) {
			return false;
		}
		return true;
	}

	public static boolean seperatingAxesTest(Vec2[] axes, Projectable p0, Projectable p1) {
		for (Vec2 axis : axes) {
			axesTested++;
			Vec2 proj0 = p0.project(axis);
			Vec2 proj1 = p1.project(axis);

			if (noProjOverlap(proj0, proj1)) {
				return false;
			}
		}
		return true;
	}

	private static boolean noProjOverlap(Vec2 proj0, Vec2 proj1) {
		return (proj0.x > proj1.y) || (proj1.x > proj0.y);
	}

	private static float projOverlap(Vec2 proj0, Vec2 proj1) {
		float d0 = proj1.y - proj0.x;
		float d1 = proj1.x - proj0.y;
		return Math.abs((d0 < -d1) ? d0 : d1);
	}

}
