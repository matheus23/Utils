package org.matheusdev.util.gameloop;

/**
 * @author matheusdev
 *
 */
public interface GameLooper extends NanoTime {

	public void init();

	public boolean isCloseRequested();

	public void tick(double delta);
	public void render(double fps);

	public void dispose();

}
