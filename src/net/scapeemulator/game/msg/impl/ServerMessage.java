package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class ServerMessage extends Message {

	private final String text;

	public ServerMessage(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
