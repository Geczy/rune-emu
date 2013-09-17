package net.scapeemulator.game.command;

import net.scapeemulator.game.model.player.Item;
import net.scapeemulator.game.model.player.Player;

public final class ItemCommandHandler extends CommandHandler {

	public ItemCommandHandler() {
		super("item");
	}

	@Override
	public void handle(Player player, String[] arguments) {
		if (player.getRights() < 2)
			return;

		if (arguments.length != 1 && arguments.length != 2) {
			player.sendMessage("Syntax: ::item [id] [amount=1]");
			return;
		}

		int id = Integer.parseInt(arguments[0]);
		int amount = 1;
		if (arguments.length == 2) {
			amount = Integer.parseInt(arguments[1]);
		}

		player.getInventory().add(new Item(id, amount));
	}

}
