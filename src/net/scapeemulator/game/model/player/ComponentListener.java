package net.scapeemulator.game.model.player;

import net.scapeemulator.game.model.player.InterfaceSet.Component;


/**
 * Written by Hadyn Richard
 */
public abstract class ComponentListener {
    
    public abstract void inputPressed(Component component, int componentId, int dynamicId);

    public abstract void componentClosed(Component component);
}
