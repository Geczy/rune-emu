package net.scapeemulator.game.msg.impl.inter;

import net.scapeemulator.game.msg.Message;

public final class InterfaceTextMessage extends Message {

	private final int id, slot;
	private final String text;

	public InterfaceTextMessage(int id, int slot, String text) {
		this.id = id;
		this.slot = slot;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public int getSlot() {
		return slot;
	}

	public String getText() {
		return text;
	}

}
