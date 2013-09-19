package net.scapeemulator.game.dialogue;

/**
 * Written by Hadyn Richard
 */
public abstract class Stage {
    
    public abstract void initializeContext(DialogueContext context);
    
    public abstract void handleOption(DialogueContext context, DialogueOption option);

}
