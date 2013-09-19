package net.scapeemulator.game.util;

/**
 * Written by Hadyn Richard
 */
public final class HandlerContext {
    
    /**
     * The flag for if the handler should stop iterating through the list.
     */
    private boolean stop;
    
    /**
     * Constructs a new {@link HandlerContext};
     */
    public HandlerContext() {}
    
    /**
     * Check if a stop was requested.
     * @return If a stop was requested.
     */
    public boolean doStop() {
        return stop;
    }
    
    /**
     * Request a stop.
     */
    public void stop() {
        stop = true;
    }
}
