package net.scapeemulator.game.msg.impl.inter;

import net.scapeemulator.game.msg.Message;

public final class InterfaceOpenMessage extends Message {
    
    public static final int CLOSABLE  = 0;
    public static final int STATIC = 1;
    public static final int DYNAMIC = 2;

    private final int id, slot, childId, mode;

    public InterfaceOpenMessage(int id, int slot, int childId, int mode) {
        this.mode = mode;
        this.id = id;
        this.slot = slot;
        this.childId = childId;
    }

    public int getId() {
        return id;
    }

    public int getSlot() {
        return slot;
    }

    public int getChildId() {
        return childId;
    }

    public int getMode() {
        return mode;
    }
}
