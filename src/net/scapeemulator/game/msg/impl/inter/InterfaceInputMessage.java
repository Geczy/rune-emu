package net.scapeemulator.game.msg.impl.inter;

import net.scapeemulator.game.msg.Message;

/**
 * Written by Hadyn Richard
 */
public final class InterfaceInputMessage extends Message {
    
    private final int id, componentId, dynamicId;
    
    public InterfaceInputMessage(int id, int componentId, int dynamicId) {
        this.id = id;
        this.componentId = componentId;
        this.dynamicId = dynamicId;
    }

    public int getId() {
        return id;
    }
    
    public int getComponentId() {
        return componentId;
    }
    
    public int getDynamicId() {
        return dynamicId;
    }
}
