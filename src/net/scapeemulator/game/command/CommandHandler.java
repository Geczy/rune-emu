package net.scapeemulator.game.command;

import net.scapeemulator.game.model.player.Player;

public abstract class CommandHandler {

	private final String name;

	public CommandHandler(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void handle(Player player, String[] arguments);

}
