package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class ScriptStringMessage extends Message {

	private final int id;
	private final String value;

	public ScriptStringMessage(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

}
