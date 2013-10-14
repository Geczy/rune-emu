package net.scapeemulator.game.player;

import java.util.HashMap;
import java.util.Map;

import net.scapeemulator.game.model.ExtendedOption;
import net.scapeemulator.game.model.World;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.player.handler.FollowHandler;

/**
 * Written by Hadyn Richard
 */
public final class PlayerDispatcher {
    
    private Map<String, PlayerHandler> handlers = new HashMap<>();
    
    public PlayerDispatcher() {
        bind(new FollowHandler());
    }
    
    public void bind(PlayerHandler handler) {
        handlers.put(handler.getOption(), handler);
    }
    
    public void unbindAll() {
        handlers.clear();
    }
    
    public void handle(Player player, int selectedId, ExtendedOption option) {
        Player selectedPlayer = World.getWorld().getPlayers().get(selectedId);
        
        if(selectedPlayer == null || !player.getPosition().isWithinScene(selectedPlayer.getPosition())) {
            return;
        }
               
        PlayerHandler handler = handlers.get(player.getOption(option.toInteger()).getText().toLowerCase());
        
        if(handler != null) {
            handler.handle(player, selectedPlayer);
        }        
    }
}