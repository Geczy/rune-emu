package net.scapeemulator.game.msg.impl.player;

import net.scapeemulator.game.model.ExtendedOption;
import net.scapeemulator.game.msg.Message;

/**
 * Written by Hadyn Richard
 */
public final class PlayerOptionMessage extends Message {
    
    private final int selectedId;
    private ExtendedOption option;
    
    public PlayerOptionMessage(int selectedId, ExtendedOption option) {
        this.selectedId = selectedId;
        this.option = option;
    }
    
    public int getSelectedId() {
        return selectedId;
    }
    
    public ExtendedOption getOption() {
        return option;
    }
}
