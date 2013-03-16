package org.matheusdev.noises.noise3;

import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.noises.noise2.Noise2;
import org.matheusdev.util.FastRand;

/**
 * Created with IntelliJ IDEA.
 * Author: matheusdev
 * Date: 3/16/13
 * Time: 5:48 PM
 */
public class SimplexNoiseLazy3 implements Noise3 {

    private static SimplexNoiseLayerLazy3[] generateLayers(int smoothness, int octaves, long seed, FloatInterpolation interp) {
        int potSmoothness = 1;
        while (potSmoothness < smoothness) {
            potSmoothness *= 2;
        }
        int density = potSmoothness;

        SimplexNoiseLayerLazy3[] layers = new SimplexNoiseLayerLazy3[octaves];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new SimplexNoiseLayerLazy3(seed, density, interp);
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

    protected final SimplexNoiseLayerLazy3[] layers;
    protected final int[] strengths;
    protected final int strengthSum;
    protected final int[] offsets;

    public SimplexNoiseLazy3(long seed, int octaves, int smoothness, FloatInterpolation interp) {
        this(seed, generateLayers(smoothness, octaves, seed, interp), generateStrengths(octaves));
    }

    public SimplexNoiseLazy3(long seed, SimplexNoiseLayerLazy3[] layers, int[] strengths) {
        if (layers.length != strengths.length)
            throw new IllegalArgumentException("layers.length != strengths.length");
        this.layers = layers;
        this.strengths = strengths;

        int sum = 0;
        for (int i = 0; i < strengths.length; i++) sum += strengths[i];
        this.strengthSum = sum;

        FastRand rand = new FastRand(seed);

        offsets = new int[layers.length*3];
        for (int i = 0; i < offsets.length; i++) {
            offsets[i] = rand.randInt(128);
        }
    }

    public float get(float x, float y, float z) {
        float val = 0f;
        for (int i = 0; i < layers.length; i++) {
            val += layers[i].get(x + offsets[i * 3], y + offsets[i * 3 + 1], z + offsets[i * 3 + 2]) * strengths[i];
        }
        return val / strengthSum;
    }

}
