package org.matheusdev.noises;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.matheusdev.PosIterationCallback;
import org.matheusdev.interpolation.FloatInterpolation;
import org.matheusdev.util.matrix.MatrixNf;

/**
 * @author matheusdev
 *
 */
public class SimplexNoiseN {

	protected static SimplexNoiseLayerN[] layersFromOctaves(final int octaves, final int smoothness, final int increase, final Random rand, final FloatInterpolation interpolator, final int... dimensions) {
		SimplexNoiseLayerN[] layers = new SimplexNoiseLayerN[octaves];
		int v = smoothness;
		for (int i = 0; i < octaves; i++) {
			layers[i] = new SimplexNoiseLayerN(v, rand, interpolator, dimensions);
			v *= increase;
		}
		return layers;
	}

	protected static float[] importancesFromOctaves(final int octaves, final int increase) {
		float[] importances = new float[octaves];
		float v = 1;
		for (int i = 0; i < octaves; i++) {
			importances[i] = v;
			v *= increase;
		}
		return importances;
	}

	protected final MatrixNf values;

	public SimplexNoiseN(final int octaves, final Random rand, final FloatInterpolation interpolator, final int... dimensions) {
		this(layersFromOctaves(octaves, 2, 2, rand, interpolator, dimensions), importancesFromOctaves(octaves, 2), rand, true, dimensions);
	}

	public SimplexNoiseN(final int octaves, final int smoothness, final int smoothnessincrease, final int importanceincrease, final Random rand, final FloatInterpolation interpolator, final int... dimensions) {
		this(layersFromOctaves(octaves, smoothness, smoothnessincrease, rand, interpolator, dimensions), importancesFromOctaves(octaves, importanceincrease), rand, true, dimensions);
	}

	public SimplexNoiseN(SimplexNoiseLayerN[] layers, float[] importances, final Random rand, boolean threaded, final int... dimensions) {
		values = new MatrixNf(dimensions);

		if (threaded) {
			ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			for (int i = 0; i < layers.length; i++) {
				final SimplexNoiseLayerN layerToGen = layers[i];
				executor.execute(new Runnable() {
					@Override
					public void run() {
						layerToGen.gen();
					}
				});
			}

			executor.shutdown();
			try {
				executor.awaitTermination(30, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 1; i < layers.length; i++) {
			final float importance = importances[i];
			if (!threaded) {
				layers[i].gen();
			}
			final MatrixNf vals = layers[i].get();

			final int[] offsets = new int[vals.getDimensions().length];
			final int[] newPos = new int[offsets.length];

			for (int j = 0; j < offsets.length; j++) {
				offsets[j] = Math.abs(rand.nextInt() % 174174);
			}

			vals.each(new PosIterationCallback() {
				@Override
				public void call(int[] pos) {
					for (int i = 0; i < newPos.length; i++) {
						newPos[i] = (pos[i] + offsets[i]) % vals.getDimensions()[i];
					}
					values.set(values.get(pos) + vals.get(newPos) * importance, pos);
				}
			});

			for (int j = 0; j < vals.values.length; j++) {
				values.values[j] += vals.values[j] * importance;
			}
		}

		float totalImportance = 0;
		for (float f : importances) {
			totalImportance += f;
		}

		for (int i = 0; i < values.values.length; i++) {
			values.values[i] /= totalImportance;
		}
	}

	public MatrixNf get() {
		return values;
	}

}
