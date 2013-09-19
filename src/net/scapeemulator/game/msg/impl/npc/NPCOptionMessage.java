package net.scapeemulator.game.msg.impl.npc;

import net.scapeemulator.game.model.Option;
import net.scapeemulator.game.msg.Message;

/**
 * Written by Hadyn Richard
 */
public final class NPCOptionMessage extends Message {
    
    private final int id;
    private final Option option;
    
    public NPCOptionMessage(int id, Option option) {
        this.id = id;
        this.option = option;
    }
    
    public int getId() {
        return id;
    }
    
    public Option getOption() {
        return option;
    }
}
