package net.scapeemulator.game.msg.impl.inter;

import net.scapeemulator.game.msg.Message;

public final class InterfaceRootMessage extends Message {

	private final int id;

	public InterfaceRootMessage(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
