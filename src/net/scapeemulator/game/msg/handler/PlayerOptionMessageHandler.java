package net.scapeemulator.game.msg.handler;

import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.MessageHandler;
import net.scapeemulator.game.msg.impl.player.PlayerOptionMessage;
import net.scapeemulator.game.player.PlayerDispatcher;

/**
 * Written by Hadyn Richard
 */
public final class PlayerOptionMessageHandler extends MessageHandler<PlayerOptionMessage> {
    
    private final PlayerDispatcher dispatcher;
    
    public PlayerOptionMessageHandler(PlayerDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void handle(Player player, PlayerOptionMessage message) {
        dispatcher.handle(player, message.getSelectedId(), message.getOption());
    }
}
