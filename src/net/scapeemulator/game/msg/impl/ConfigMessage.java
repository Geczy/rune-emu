package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class ConfigMessage extends Message {

	private final int id, value;

	public ConfigMessage(int id, int value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public int getValue() {
		return value;
	}

}
