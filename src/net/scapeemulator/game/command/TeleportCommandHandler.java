package net.scapeemulator.game.command;

import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.Position;

public final class TeleportCommandHandler extends CommandHandler {

	public TeleportCommandHandler() {
		super("tele");
	}

	@Override
	public void handle(Player player, String[] arguments) {
		if (player.getRights() < 2)
			return;

		if (arguments.length != 2 && arguments.length != 3) {
			player.sendMessage("Syntax: ::tele [x] [y] [height=0]");
			return;
		}

		int x = Integer.parseInt(arguments[0]);
		int y = Integer.parseInt(arguments[1]);
		int height = player.getPosition().getHeight();

		if (arguments.length == 3)
			height = Integer.parseInt(arguments[2]);

		player.teleport(new Position(x, y, height));
	}

}
