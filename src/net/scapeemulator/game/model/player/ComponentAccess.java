package net.scapeemulator.game.model.player;

/**
 * Written by Hadyn Richard
 */
public final class ComponentAccess {
    
    /**
     * The value for when there are no access set.
     */
    private static final int UNSET = 0x0;
    
    /**
     * The option bit offset.
     */
    private static final int OPTION_OFFSET = 1;
    
    /**
     * The active flags.
     */
    private int flags;
    
    /**
     * Constructs a new {@link ComponentAccess};
     */
    public ComponentAccess() {
        this(UNSET);
    }
    
    /**
     * Constructs a new {@link ComponentAccess};
     * @param flags The flags.
     */
    public ComponentAccess(int flags) {
        this.flags = flags;
    }
    
    /**
     * Sets an option as active.
     * @param id The option id.
     */
    public void setOptionActive(int id) {
        flags |= 1 << (id - 1) + OPTION_OFFSET;
    }
    
    /**
     * Sets an option as inactive.
     * @param id The option id.
     */
    public void setOptionInactive(int id) {
        flags &= 0xffffffff - 1 << (id - 1) + OPTION_OFFSET;
    }
    
    /**
     * Gets the flags.
     * @return The flags.
     */
    public int getFlags() {
        return flags;
    }
}
