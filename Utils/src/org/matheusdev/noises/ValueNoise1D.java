package org.matheusdev.noises;

import java.util.Random;

/**
 * @author matheusdev
 *
 */
public class ValueNoise1D {

	protected final float[] values;
	protected final int size;
	protected final Random rand;

	protected float minchange;
	protected float change;

	public ValueNoise1D(int size, float seedLeft, float seedRight, float minchange, float smoothness, final Random rand) {
		this.size = size;

		size = 2;
		while (size < this.size-1) {
			size *= 2;
		}
		size += 1;

		this.minchange = minchange;
		this.change = size/smoothness;
		this.rand = rand;

		this.values = new float[size];
		values[0] 				= seedLeft;
		values[values.length-1] = seedRight;
	}

	public ValueNoise1D gen() {
		step(0, values.length-1);
		return this;
	}

	public int length() {
		return size;
	}

	public float get(int i) {
		rangeCheck(i);
		return values[i];
	}

	public float[] get() {
		float[] vals = new float[size];
		System.arraycopy(values, 0, vals, 0, size);
		return vals;
	}

	public float[] getFully() {
		return values;
	}

	protected void rangeCheck(int i) {
		if (i < 0 || i >= size) {
			throw new IllegalArgumentException("i < 0 || i >= size");
		}
	}

	protected void step(int start, int len) {
		int halfLen = len/2;

		if (len <= 1) return;

		values[start+halfLen] = next(values[start], values[start+len]);
		change = Math.min(-minchange, Math.max(minchange, change/2));

		step(start, halfLen);
		step(start+halfLen, halfLen);
	}

	protected float next(float val1, float val2) {
		return ((val1 + val2)/2) + nextRand();
	}

	protected float nextRand() {
		return rand.nextBoolean() ?
				(rand.nextFloat() * change) :
				(-rand.nextFloat() * change);
	}

}
