package net.scapeemulator.game.player.handler;

import net.scapeemulator.game.model.mob.action.FollowAction;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.player.PlayerHandler;

/**
 * Written by Hadyn Richard
 */
public final class FollowHandler extends PlayerHandler {
    
    public FollowHandler() {
        super("follow");
    }

    @Override
    public void handle(Player player, Player selectedPlayer) {
        player.startAction(new FollowAction(player, selectedPlayer));
    }
}