package net.scapeemulator.game.msg.handler.inter;

import net.scapeemulator.game.model.player.InterfaceSet.Component;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.MessageHandler;
import net.scapeemulator.game.msg.impl.inter.InterfaceInputMessage;

/**
 * Written by Hadyn Richard
 */
public final class InterfaceInputMessageHandler extends MessageHandler<InterfaceInputMessage> {

    @Override
    public void handle(Player player, InterfaceInputMessage message) {
        Component component = player.getInterfaceSet().getComponent(message.getId());
        System.out.println("id: " + message.getId() + ", comp: " + message.getComponentId() + ", dyn: " + message.getDynamicId());
        if(component != null) {
            component.alertInputPressed(message.getComponentId(), message.getDynamicId());
        }
    }
}
