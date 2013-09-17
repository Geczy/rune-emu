package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class ClickMessage extends Message {

	private final int time, x, y;
	private final boolean rightClick;

	public ClickMessage(int time, int x, int y, boolean rightClick) {
		this.time = time;
		this.x = x;
		this.y = y;
		this.rightClick = rightClick;
	}

	public int getTime() {
		return time;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isRightClick() {
		return rightClick;
	}

}
