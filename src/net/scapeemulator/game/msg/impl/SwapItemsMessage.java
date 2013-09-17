package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class SwapItemsMessage extends Message {

	private final int id, slot, source, destination, type;

	public SwapItemsMessage(int id, int slot, int source, int destination, int type) {
		this.id = id;
		this.slot = slot;
		this.source = source;
		this.destination = destination;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public int getSlot() {
		return slot;
	}

	public int getSource() {
		return source;
	}

	public int getDestination() {
		return destination;
	}

	public int getType() {
		return type;
	}

}
