package org.matheusdev.util.gameloop;

/**
 * @author matheusdev
 *
 */
public interface GameLooper extends NanoTime {

	public void init(FpsUpdater fpsUpdater, FpsUpdater upsUpdater);

	public boolean isCloseRequested();

	public void tick(double delta);
	public void render();

	public void dispose();

}
