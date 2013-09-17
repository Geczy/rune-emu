package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class DisplayMessage extends Message {

	private final int mode, width, height;

	public DisplayMessage(int mode, int width, int height) {
		this.mode = mode;
		this.width = width;
		this.height = height;
	}

	public int getMode() {
		return mode;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
