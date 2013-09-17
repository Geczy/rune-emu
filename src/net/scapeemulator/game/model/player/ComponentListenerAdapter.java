package net.scapeemulator.game.model.player;

import net.scapeemulator.game.model.player.InterfaceSet.Component;

/**
 * Written by Hadyn Richard
 */
public abstract class ComponentListenerAdapter extends ComponentListener {

    @Override
    public void inputPressed(Component component, int componentId, int dynamicId) {}

    @Override
    public void componentClosed(Component component) {}

}
