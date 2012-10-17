package org.matheusdev.util.gameloop;


/**
 * @author matheusdev
 *
 */
public class FpsUpdater {

	public static final long NANOS_PER_SECOND = 1_000_000_000L;

	protected final NanoTime time;
	protected final long updateFps;

	protected long lastTick;
	protected long lastFpsTick;
	protected long frames;
	protected double fps;

	public FpsUpdater(NanoTime time, long updateFps) {
		this.time = time;
		this.updateFps = updateFps;
		this.fps = updateFps;
		lastTick = time.getNanoTime();
		lastFpsTick = time.getNanoTime();
	}

	public double getFps() {
		return fps;
	}

	/**
	 * @return the delta time
	 * 		(1 when targetFps = 60 && currentFps = 60)
	 * 		(0.5 when targetFps = 60 && currentFps = 30);
	 */
	public double next() {
		computeFps();
		return getDelta();
	}

	protected double getDelta() {
		long now = time.getNanoTime();
		double delta = (double) (now - lastTick) / NANOS_PER_SECOND * updateFps;
		lastTick = now;
		return delta;
	}

	protected void computeFps() {
		frames++;

		if (frames >= updateFps) {
			long now = time.getNanoTime();
			fps = NANOS_PER_SECOND / (double) (now - lastFpsTick) * updateFps;
			lastFpsTick = now;
			frames = 0;
		}
	}

}
