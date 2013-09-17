package net.scapeemulator.game.command;

import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.Position;

public final class PositionCommandHandler extends CommandHandler {

	public PositionCommandHandler() {
		super("pos");
	}

	@Override
	public void handle(Player player, String[] arguments) {
		if (player.getRights() < 2)
			return;

		if (arguments.length != 0) {
			player.sendMessage("Syntax: ::pos");
			return;
		}

		Position position = player.getPosition();
		player.sendMessage("You are at: " + position.getX() + ", " + position.getY() + ", " + position.getHeight());
	}

}
