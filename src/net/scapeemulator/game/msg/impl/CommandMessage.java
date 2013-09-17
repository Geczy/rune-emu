package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class CommandMessage extends Message {

	private final String command;

	public CommandMessage(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

}
