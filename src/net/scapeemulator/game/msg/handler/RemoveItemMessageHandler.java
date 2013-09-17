package net.scapeemulator.game.msg.handler;

import net.scapeemulator.game.model.player.Equipment;
import net.scapeemulator.game.model.player.Interface;
import net.scapeemulator.game.model.player.Item;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.MessageHandler;
import net.scapeemulator.game.msg.impl.RemoveItemMessage;

public final class RemoveItemMessageHandler extends MessageHandler<RemoveItemMessage> {

	@Override
	public void handle(Player player, RemoveItemMessage message) {
		if (message.getId() == Interface.EQUIPMENT && message.getSlot() == 28) {
			Item item = player.getEquipment().get(message.getItemSlot());
			if (item == null || item.getId() != message.getItemId())
				return;

			Equipment.remove(player, message.getItemSlot());
		}
	}

}
