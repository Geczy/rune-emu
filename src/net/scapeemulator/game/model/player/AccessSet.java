package net.scapeemulator.game.model.player;

import java.util.HashMap;
import java.util.Map;
import net.scapeemulator.game.model.Widget;
import net.scapeemulator.game.msg.impl.inter.InterfaceAccessMessage;

/**
 * Written by Hadyn Richard
 */
public final class AccessSet {
    
    private final Map<Integer, ComponentAccess> accessMap = new HashMap<>(); 
    
    private final Player player;
    
    public AccessSet(Player player) {
        this.player = player;
    }
    
    public void setOptionActive(int optionId, int id, int componentId, int childStart, int childEnd) {
        ComponentAccess access = getComponentAccess(id, componentId);
        access.setOptionActive(optionId);
        player.send(new InterfaceAccessMessage(id, componentId, childStart, childEnd, access.getFlags()));
    }
    
    public void setOptionsActive(int optionStart, int optionEnd, int id, int componentId, int childStart, int childEnd) {        
        ComponentAccess access = getComponentAccess(id, componentId);
        for(int option = optionStart; option <= optionEnd; option++) {
            access.setOptionActive(option);
        }
        player.send(new InterfaceAccessMessage(id, componentId, childStart, childEnd, access.getFlags()));
    }
    
    private ComponentAccess getComponentAccess(int id, int componentId) {
        int hash = Widget.getHash(id, componentId);
        ComponentAccess access = accessMap.get(hash);
        if(access == null) {
           access = new ComponentAccess(); 
           accessMap.put(hash, access);
        }
        return access;
    }
}
