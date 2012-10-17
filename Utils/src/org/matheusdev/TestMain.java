package org.matheusdev;

import java.util.Random;

import org.matheusdev.util.gameloop.GameLoop;
import org.matheusdev.util.gameloop.GameLooper;


/**
 * @author matheusdev
 *
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameLoop loop = new GameLoop(new GameLooper() {
			final Random rand;
			{
				rand = new Random();
			}
			@Override
			public long getNanoTime() {
				return System.nanoTime();
			}
			@Override
			public void tick(double delta) {
				try {
					// Simulate "lag"
					Thread.sleep(Math.abs(rand.nextInt() % 8));
				} catch (InterruptedException e) {}
				System.out.println("tick (delta: " + delta + ")");
			}
			@Override
			public void render(double fps) {
				System.out.println("render (fps: " + fps + ")");
			}
			@Override
			public boolean isCloseRequested() {
				return false;
			}
			@Override
			public void init() {
			}
			@Override
			public void dispose() {
			}
		}, GameLoop.Type.LIMITED_RENDER, 60);
		loop.start();
	}

}
