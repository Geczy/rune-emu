package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class FlagsMessage extends Message {

	private final int flags;

	public FlagsMessage(int flags) {
		this.flags = flags;
	}

	public int getFlags() {
		return flags;
	}

}
