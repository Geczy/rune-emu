package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class WalkMessage extends Message {

	public static final class Step {

		private final int x, y;

		public Step(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

	}

	private final Step[] steps;
	private final boolean running;

	public WalkMessage(Step[] steps, boolean running) {
		this.steps = steps;
		this.running = running;
	}

	public Step[] getSteps() {
		return steps;
	}

	public boolean isRunning() {
		return running;
	}

}
