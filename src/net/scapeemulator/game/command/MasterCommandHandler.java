package net.scapeemulator.game.command;

import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.player.SkillSet;

public final class MasterCommandHandler extends CommandHandler {

	public MasterCommandHandler() {
		super("master");
	}

	@Override
	public void handle(Player player, String[] arguments) {
		if (player.getRights() < 2)
			return;

		if (arguments.length != 0) {
			player.sendMessage("Syntax: ::master");
			return;
		}

		SkillSet skills = player.getSkillSet();
		for (int id = 0; id < skills.size(); id++)
			skills.addExperience(id, SkillSet.MAXIMUM_EXPERIENCE);
	}

}
