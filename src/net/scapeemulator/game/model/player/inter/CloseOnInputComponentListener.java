package net.scapeemulator.game.model.player.inter;

import net.scapeemulator.game.model.player.ComponentListenerAdapter;
import net.scapeemulator.game.model.player.InterfaceSet.Component;

/**
 * Written by Hadyn Richard
 */
public final class CloseOnInputComponentListener extends ComponentListenerAdapter {

    @Override
    public void inputPressed(Component component, int componentId, int dynamicId) {
        component.removeListener();
        component.reset();
    }
}
