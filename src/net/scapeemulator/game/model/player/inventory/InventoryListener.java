package net.scapeemulator.game.model.player.inventory;

import net.scapeemulator.game.model.player.Item;

public interface InventoryListener {

	public void itemChanged(Inventory inventory, int slot, Item item);

	public void itemsChanged(Inventory inventory);

	public void capacityExceeded(Inventory inventory);

}
