package org.matheusdev.util.gameloop;


/**
 * @author matheusdev
 *
 */
public class GameLoop implements Runnable {

	public static final long NANOS_PER_SECOND = 1_000_000_000L;

	public static enum Type {
		NO_LIMIT,
		LIMITED_DELTA,
		LIMITED_RENDER
	}

	protected final GameLooper looper;
	protected final Type type;
	protected final long framerate;

	protected volatile boolean stopped;
	protected volatile boolean paused;
	protected Thread runner;
	protected ThreadGroup group;

	public GameLoop(GameLooper looper, Type type, long framerate) {
		this.looper = looper;
		this.type = type;
		this.framerate = framerate;
	}

	public synchronized void start() {
		if (runner == null) {
			group = new ThreadGroup("GameLoop Group");
			runner = new Thread(group, this, "GameLoop");
			runner.setPriority(Thread.MAX_PRIORITY);
			runner.start();
		}
	}

	public synchronized void stop() {
		if (runner != null) {
			stopped = true;
			runner = null;
		}
	}

	public void pause() {
		synchronized (this) {
			paused = true;
		}
	}

	public void resume() {
		synchronized (this) {
			paused = false;
		}
	}

	public boolean isPaused() {
		return paused;
	}

	@Override
	public void run() {
		looper.init();

		try {
			switch (type) {
			case NO_LIMIT:
				runSimple();
				break;
			case LIMITED_DELTA:
				runDelta();
				break;
			case LIMITED_RENDER:
				runLimited();
				break;
			}
		} catch (Throwable e) {
			System.err.println("Exception occured in GameLoop thread:");
			throw e;
		} finally {
			looper.dispose();
		}
	}

	protected void runSimple() {
		FpsUpdater fps = new FpsUpdater(looper, framerate);
		while (!looper.isCloseRequested() && !stopped) {
			looper.tick(fps.next());
			looper.render(fps.getFps());
		}
	}

	protected void runDelta() {
		FpsUpdater fps = new FpsUpdater(looper, framerate);
		Sync sync = new Sync() {
			@Override
			public long getTime() {
				return System.nanoTime();
			}
		};
		while (!looper.isCloseRequested() && !stopped) {
			looper.tick(fps.next());
			looper.render(fps.getFps());
			sync.sync(framerate);
		}
	}

	protected void runLimited() {
		FpsUpdater fps = new FpsUpdater(looper, framerate);
		FpsUpdater ups = new FpsUpdater(looper, framerate);
		long lastRender = looper.getNanoTime();
		long maxTime = NANOS_PER_SECOND / framerate;
		long lastTickDuration = 0;
		long tickBeginTime = looper.getNanoTime();
		long now = looper.getNanoTime();

		while (!looper.isCloseRequested() && !stopped) {
			do {
				tickBeginTime = looper.getNanoTime();
				ups.next();
				looper.tick(1.0);
				now = looper.getNanoTime();
				lastTickDuration = (now - tickBeginTime);
			} while ((looper.getNanoTime() - lastRender) + lastTickDuration < maxTime);

			lastRender = looper.getNanoTime();
			fps.next();
			looper.render(fps.getFps());
		}
	}

}
