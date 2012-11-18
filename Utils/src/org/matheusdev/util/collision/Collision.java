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

	public static boolean intersect(Rect r1, Rect r2) {
		// Sperating axis theorem simplified for AABBes.
		if ((r1.x >= (r2.x + r2.w)) || ((r1.x + r1.w) <= r2.x)) {
			return false;
		}
		if ((r1.y >= (r2.y + r2.h)) || ((r1.y + r1.h) <= r2.y)) {
			return false;
		}
		return true;
	}

	public static boolean contains(Rect r, Vec2 vec) {
		return (vec.x >= r.x && vec.y >= r.y && vec.x < r.right() && vec.y < r.bottom());
	}

	public static MinimalTranslationVector getSAT(SATObject obj0, SATObject obj1) {
		if (intersect(obj0.getAABB(), obj1.getAABB())) {
			objectsTested++;
			double overlap = Double.MAX_VALUE;
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
					double o = projOverlap(proj0, proj1);

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
					double o = projOverlap(proj0, proj1);

					if (o < overlap) {
						overlap = o;
						smallest = axis;
					}
				}
			}
			return new MinimalTranslationVector(smallest, overlap);
		} else {
			return null;
		}
	}

	public static boolean testSAT(SATObject obj0, SATObject obj1) {
		if (intersect(obj0.getAABB(), obj1.getAABB())) {
			objectsTested++;
			Vec2[] axes0 = obj0.getAxes();
			Vec2[] axes1 = obj1.getAxes();

			for (Vec2 axis : axes0) {
				axesTested++;
				Vec2 proj0 = obj0.project(axis);
				Vec2 proj1 = obj1.project(axis);

				if (noProjOverlap(proj0, proj1)) {
					return false;
				}
			}

			for (Vec2 axis : axes1) {
				axesTested++;
				Vec2 proj0 = obj0.project(axis);
				Vec2 proj1 = obj1.project(axis);

				if (noProjOverlap(proj0, proj1)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private static boolean noProjOverlap(Vec2 proj0, Vec2 proj1) {
		return (proj0.x > proj1.y) || (proj1.x > proj0.y);
	}

	private static float projOverlap(Vec2 proj0, Vec2 proj1) {
		return Math.min(Math.abs(proj0.x - proj1.y), Math.abs(proj0.y - proj1.x));
	}

}
