package net.scapeemulator.game.msg.impl.inter;

import net.scapeemulator.game.msg.Message;

/**
 * Written by Hadyn Richard
 */
public final class InterfacePlayerHeadMessage extends Message {
    
    private final int id, componentId;
    
    public InterfacePlayerHeadMessage(int id, int componentId) {
        this.id = id;
        this.componentId = componentId;
    }
    
    public int getId() {
        return id;
    }
    
    public int getComponentId() {
        return componentId;
    }
}