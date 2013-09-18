package net.scapeemulator.game.msg.impl.inter;

import net.scapeemulator.game.msg.Message;

/**
 * Written by Hadyn Richard
 */
public class InterfaceNPCHeadMessage extends Message {
    
    private final int id, componentId, npcId;
    
    public InterfaceNPCHeadMessage(int id, int componentId, int npcId) {
        this.id = id;
        this.componentId = componentId;
        this.npcId = npcId;
    }
    
    public int getId() {
        return id;
    }
    
    public int getComponentId() {
        return componentId;
    }
    
    public int getNpcId() {
        return npcId;
    }
}
