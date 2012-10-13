package org.matheusdev;

import java.util.Random;

import org.matheusdev.noises.ValueNoise1D;


/**
 * @author matheusdev
 *
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random rand = new Random();
		ValueNoise1D noise = new ValueNoise1D(257, (rand.nextInt()%50)+25, (rand.nextInt()%50)+25, 1.6f, 2f, rand).gen();
		for (int i = 0; i < noise.length(); i++) {
			for (int j = 0; j < noise.get(i)+25; j++) {
				System.out.print("#");
			}
			System.out.println();
		}
	}

}
