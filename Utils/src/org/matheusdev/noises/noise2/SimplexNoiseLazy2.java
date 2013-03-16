package org.matheusdev.noises.noise2;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.util.FastRand;

/**
 * Created with IntelliJ IDEA.
 * Author: matheusdev
 * Date: 3/16/13
 * Time: 5:48 PM
 */
public class SimplexNoiseLazy2 implements Noise2 {

    private static SimplexNoiseLayerLazy2[] generateLayers(int smoothness, int octaves, long seed, FloatInterpolation interp) {
        int potSmoothness = 1;
        while (potSmoothness < smoothness) {
            potSmoothness *= 2;
        }
        int density = potSmoothness;

        SimplexNoiseLayerLazy2[] layers = new SimplexNoiseLayerLazy2[octaves];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new SimplexNoiseLayerLazy2(seed, density, interp);
            density *= 2;
        }
        return layers;
    }

    private static int[] generateStrengths(int octaves) {
        int strength = 1;
        int[] strengths = new int[octaves];
        for (int i = 0; i < octaves; i++) {
            strengths[i] = strength;
            strength *= 2;
        }
        return strengths;
    }

    protected final SimplexNoiseLayerLazy2[] layers;
    protected final int[] strengths;
    protected final int strengthSum;
    protected final int[] offsets;

    public SimplexNoiseLazy2(long seed, int octaves, int smoothness, FloatInterpolation interp) {
        this(seed, generateLayers(smoothness, octaves, seed, interp), generateStrengths(octaves));
    }

    public SimplexNoiseLazy2(long seed, SimplexNoiseLayerLazy2[] layers, int[] strengths) {
        if (layers.length != strengths.length)
            throw new IllegalArgumentException("layers.length != strengths.length");
        this.layers = layers;
        this.strengths = strengths;

        int sum = 0;
        for (int i = 0; i < strengths.length; i++) sum += strengths[i];
        this.strengthSum = sum;

        FastRand rand = new FastRand(seed);

        offsets = new int[layers.length*2];
        for (int i = 0; i < offsets.length; i++) {
            offsets[i] = rand.randInt(128);
        }
    }

    public float get(float x, float y) {
        float val = 0f;
        for (int i = 0; i < layers.length; i++) {
            val += layers[i].get(x + offsets[i * 2], y + offsets[i * 2 + 1]) * strengths[i];
        }
        return val / strengthSum;
    }

}
