package net.scapeemulator.game.model;

public final class SpotAnimation {

	private final int id, delay, height;

	public SpotAnimation(int id) {
		this(id, 0, 0);
	}

	public SpotAnimation(int id, int delay) {
		this(id, delay, 0);
	}

	public SpotAnimation(int id, int delay, int height) {
		this.id = id;
		this.delay = delay;
		this.height = height;
	}

	public int getId() {
		return id;
	}

	public int getDelay() {
		return delay;
	}

	public int getHeight() {
		return height;
	}

}
