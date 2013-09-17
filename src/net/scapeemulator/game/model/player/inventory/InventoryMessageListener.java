package net.scapeemulator.game.model.player.inventory;

import net.scapeemulator.game.model.player.Item;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.player.SlottedItem;
import net.scapeemulator.game.msg.impl.inter.InterfaceItemsMessage;
import net.scapeemulator.game.msg.impl.inter.InterfaceResetItemsMessage;
import net.scapeemulator.game.msg.impl.inter.InterfaceSlottedItemsMessage;

public final class InventoryMessageListener implements InventoryListener {

	private final Player player;
	private final int id, child, type;

	public InventoryMessageListener(Player player, int id, int child, int type) {
		this.player = player;
		this.id = id;
		this.child = child;
		this.type = type;
	}

	@Override
	public void itemChanged(Inventory inventory, int slot, Item item) {
		SlottedItem[] items = new SlottedItem[] {
			new SlottedItem(slot, item)
		};
		player.send(new InterfaceSlottedItemsMessage(id, child, type, items));
	}

	@Override
	public void itemsChanged(Inventory inventory) {
		if (inventory.isEmpty()) {
			// TODO: consider how this interacts with the 'type'?
			player.send(new InterfaceResetItemsMessage(id, child));
		} else {
			Item[] items = inventory.toArray();
			player.send(new InterfaceItemsMessage(id, child, type, items));
		}
	}

	@Override
	public void capacityExceeded(Inventory inventory) {
		/* empty */
	}

}
