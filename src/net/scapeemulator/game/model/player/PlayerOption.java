package net.scapeemulator.game.model.player;

/**
 * Written by Hadyn Richard
 */
public final class PlayerOption {
    
    public static final int TRADE_OPTION  = 2;
    public static final int FOLLOW_OPTION = 3;
    
    /**
     * The default option string.
     */
    private static final String DEFAULT_OPTION = "null";
    
    /**
     * The option string for the option.
     */
    private String option;
    
    /**
     * The flag for if the option is currently at the top.
     */
    private boolean atTop;
    
    /**
     * Constructs a new {@link PlayerOption};
     */
    public PlayerOption() {
        option = DEFAULT_OPTION;
    }
    
    /**
     * Sets if the option is at the top.
     */
    public void setAtTop(boolean atTop) {
        this.atTop = atTop;
    }
    
    /**
     * Gets if the option is at the top.
     */
    public boolean atTop() {
        return atTop;
    }
    
    /**
     * Sets the option string.
     */
    public void setText(String option) {
        this.option = option;
    }
    
    /**
     * Gets the option string.
     */
    public String getText() {
        return option;
    }
    
    /**
     * Resets the option.
     */
    public void reset() {
        option = DEFAULT_OPTION;
    }
}
