package net.scapeemulator.game.util.math;

public final class Trigonometry {

	public static final int[] COSINE = new int[2048];

	static {
		for (int i = 0; i < COSINE.length; i++) {
			COSINE[i] = (int) (65536 * Math.cos(2 * Math.PI * i / (double) COSINE.length));
		}
	}	

	private Trigonometry() {

	}

}
