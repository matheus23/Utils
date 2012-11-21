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
package org.matheusdev.util.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.matheusdev.util.collision.Rect;
import org.matheusdev.util.vecs.Vec2i;

/**
 * @author matheusdev
 *
 */
public class GridCollection<E extends CollectionEntity> {

	protected final HashMap<Vec2i, ArrayList<E>> map;
	protected final ArrayList<E> queryList;
	protected int width;
	protected int height;

	public GridCollection(int elementWidth, int elementHeight) {
		this.width = elementWidth;
		this.height = elementHeight;
		this.map = new HashMap<>();
		this.queryList = new ArrayList<>();
	}

	public void add(E e) {
		Rect rect = e.getAABB();

		int beginx = ((int) rect.x) / width;
		int beginy = ((int) rect.y) / height;
		int endx = ((int) (rect.x + rect.w)) / width;
		int endy = ((int) (rect.y + rect.h)) / height;

		Vec2i pos = new Vec2i(0, 0);
		for (int y = beginy; y <= endy; y++) {
			for (int x = beginx; x <= endx; x++) {
				pos.set(x, y);

				ArrayList<E> mapList = map.get(pos);

				if (mapList == null) {
					ArrayList<E> list = new ArrayList<E>();
					list.add(e);
					map.put(new Vec2i(x, y), list);
				} else {
					mapList.add(e);
				}
			}
		}
	}

	public void query(CollectionTraverser<E> callback, Rect region) {
		int beginx = ((int) region.x) / width;
		int beginy = ((int) region.y) / height;
		int endx = ((int) (region.x + region.w)) / width;
		int endy = ((int) (region.y + region.h)) / height;

		for (int y = beginy; y <= endy; y++) {
			for (int x = beginx; x <= endx; x++) {
				Vec2i pos = Vec2i.get(x, y);
				ArrayList<E> elements = map.get(pos);

				if (elements != null) {
					for (int i = 0; i < elements.size(); i++) {
						boolean continueSearching = callback.handleElement(x, y, elements.get(i));

						if (!continueSearching) {
							return;
						}
					}
				}
			}
		}
	}

	public List<E> query(Rect region) {
		queryList.clear();

		int beginx = ((int) region.x) / width;
		int beginy = ((int) region.y) / height;
		int endx = ((int) (region.x + region.w)) / width;
		int endy = ((int) (region.y + region.h)) / height;

		for (int y = beginy; y <= endy; y++) {
			for (int x = beginx; x <= endx; x++) {
				Vec2i pos = Vec2i.get(x, y);
				ArrayList<E> elements = map.get(pos);

				if (elements != null) {
					queryList.addAll(elements);
				}
			}
		}
		return queryList;
	}

	public void update() {
		update(width, height);
	}

	/**
	 * <p>Use this only in case you want to change the
	 * width and height according to latest statistics
	 * or something like that.</p>
	 * @param newWidth
	 * @param newHeight
	 */
	public void update(int newWidth, int newHeight) {
		this.width = newWidth;
		this.height = newHeight;

		Iterator<Entry<Vec2i, ArrayList<E>>> itr = map.entrySet().iterator();

		while (itr.hasNext()) {
			Entry<Vec2i, ArrayList<E>> entry = itr.next();
			ArrayList<E> list = entry.getValue();
			// So this part is basically "Garbage-Collection"...
			//
			// The lists, which are empty, haven't got any
			// element inside them since they were created,
			// which means they aren't used, and probably
			// won't be used later.
			// Because of that we just let the GC Kill
			// those ArrayLists...
			if (list.isEmpty()) {
				// This comment is somehow famous...
				// (For reference, see source code from "ArrayList#clear()"...)
				// Let gc do its work
				map.put(entry.getKey(), null);
			} else {
				list.clear();
			}
		}
	}

}
