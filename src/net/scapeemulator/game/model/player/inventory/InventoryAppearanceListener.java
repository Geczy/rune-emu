package net.scapeemulator.game.model.player.inventory;

import net.scapeemulator.game.model.player.Item;
import net.scapeemulator.game.model.player.Player;

public final class InventoryAppearanceListener implements InventoryListener {

	private final Player player;

	public InventoryAppearanceListener(Player player) {
		this.player = player;
	}

	@Override
	public void itemChanged(Inventory inventory, int slot, Item item) {
		player.setAppearance(player.getAppearance());
	}

	@Override
	public void itemsChanged(Inventory inventory) {
		player.setAppearance(player.getAppearance());
	}

	@Override
	public void capacityExceeded(Inventory inventory) {
		/* empty */
	}

}
