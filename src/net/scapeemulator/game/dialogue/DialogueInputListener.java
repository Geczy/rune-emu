package net.scapeemulator.game.dialogue;

import net.scapeemulator.game.model.player.ComponentListenerAdapter;
import net.scapeemulator.game.model.player.InterfaceSet;
import net.scapeemulator.game.model.player.InterfaceSet.Component;

/**
 * Written by Hadyn Richard
 */
public final class DialogueInputListener extends ComponentListenerAdapter {
    
    private final DialogueContext context;
    
    public DialogueInputListener(DialogueContext context) {
        this.context = context;
    }

    @Override
    public void inputPressed(Component component, int componentId, int dynamicId) {
        context.handleInput(component.getCurrentId(), componentId);
    }
}
