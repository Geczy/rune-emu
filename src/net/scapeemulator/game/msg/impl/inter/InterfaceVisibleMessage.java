package net.scapeemulator.game.msg.impl.inter;

import net.scapeemulator.game.msg.Message;

public final class InterfaceVisibleMessage extends Message {

	private final int id, slot;
	private final boolean visible;

	public InterfaceVisibleMessage(int id, int slot, boolean visible) {
		this.id = id;
		this.slot = slot;
		this.visible = visible;
	}

	public int getId() {
		return id;
	}

	public int getSlot() {
		return slot;
	}

	public boolean isVisible() {
		return visible;
	}

}
