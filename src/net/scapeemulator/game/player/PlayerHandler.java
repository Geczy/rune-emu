package net.scapeemulator.game.player;

import net.scapeemulator.game.model.player.Player;

/**
 * Written by Hadyn Richard
 */
public abstract class PlayerHandler {
    
    private String option;
    
    public PlayerHandler(String option) {
        this.option = option;
    }
    
    public String getOption() {
        return option;
    }
    
    public abstract void handle(Player player, Player selectedPlayer);
}
