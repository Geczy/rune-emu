package net.scapeemulator.game.msg.impl.inter;

import net.scapeemulator.game.model.player.SlottedItem;
import net.scapeemulator.game.msg.Message;

public final class InterfaceSlottedItemsMessage extends Message {

	private final int id, slot, type;
	private final SlottedItem[] items;

	public InterfaceSlottedItemsMessage(int id, int slot, int type, SlottedItem[] items) {
		this.id = id;
		this.slot = slot;
		this.type = type;
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public int getSlot() {
		return slot;
	}

	public int getType() {
		return type;
	}

	public SlottedItem[] getItems() {
		return items;
	}

}
