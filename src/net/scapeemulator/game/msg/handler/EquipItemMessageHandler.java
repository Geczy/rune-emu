package net.scapeemulator.game.msg.handler;

import net.scapeemulator.game.model.player.Equipment;
import net.scapeemulator.game.model.player.Interface;
import net.scapeemulator.game.model.player.Item;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.MessageHandler;
import net.scapeemulator.game.msg.impl.EquipItemMessage;

public final class EquipItemMessageHandler extends MessageHandler<EquipItemMessage> {

	public void handle(Player player, EquipItemMessage message) {
		if (message.getId() == Interface.INVENTORY && message.getSlot() == 0) {
			Item item = player.getInventory().get(message.getItemSlot());
			if (item == null || item.getId() != message.getItemId())
				return;

			Equipment.equip(player, message.getItemSlot());
		}
	}

}

