package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

/**
 * Written by Hadyn Richard
 */
public final class PlayerMenuOptionMessage extends Message {
    
    private final int id;
    private final boolean atTop;
    private final String option;
    
    public PlayerMenuOptionMessage(int id, boolean atTop, String option) {
        this.id = id;
        this.atTop = atTop;
        this.option = option;
    }
    
    public int getId() {
        return id;
    }
    
    public boolean isAtTop() {
        return atTop;
    }
    
    public String getOption() {
        return option;
    }
}
