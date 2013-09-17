package net.scapeemulator.game.msg.handler;

import net.scapeemulator.game.command.CommandDispatcher;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.MessageHandler;
import net.scapeemulator.game.msg.impl.CommandMessage;

public final class CommandMessageHandler extends MessageHandler<CommandMessage> {

    private final CommandDispatcher dispatcher;

	public CommandMessageHandler(CommandDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

	@Override
	public void handle(Player player, CommandMessage message) {
		dispatcher.handle(player, message.getCommand());
	}

}
