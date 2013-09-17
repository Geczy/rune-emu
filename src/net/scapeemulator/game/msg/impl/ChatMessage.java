package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class ChatMessage extends Message {

	private final int color, effects;
	private final String text;

	public ChatMessage(int color, int effects, String text) {
		this.color = color;
		this.effects = effects;
		this.text = text;
	}

	public int getColor() {
		return color;
	}

	public int getEffects() {
		return effects;
	}

	public String getText() {
		return text;
	}

}
