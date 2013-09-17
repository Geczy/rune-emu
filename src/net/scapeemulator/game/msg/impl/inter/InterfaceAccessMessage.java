package net.scapeemulator.game.msg.impl.inter;

import net.scapeemulator.game.msg.Message;

/**
 * Written by Hadyn Richard
 */
public final class InterfaceAccessMessage extends Message {
    
    private final int id, componentId, childStart, childEnd, flags;
    
    public InterfaceAccessMessage(int id, int componentId, int childStart, int childEnd, int flags) {
        this.id = id;
        this.componentId = componentId;
        this.childStart = childStart;
        this.childEnd = childEnd;
        this.flags = flags;
    }
    
    public int getId() {
        return id;
    }
    
    public int getComponentId() {
        return componentId;
    }
    
    public int getChildStart() {
        return childStart;
    }
    
    public int getChildEnd() {
        return childEnd;
    }
    
    public int getFlags() {
        return flags;
    }

}
