package net.scapeemulator.game.msg.handler;

import net.scapeemulator.game.model.player.Interface;
import net.scapeemulator.game.model.player.InterfaceSet;
import net.scapeemulator.game.model.player.InterfaceSet.DisplayMode;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.MessageHandler;
import net.scapeemulator.game.msg.impl.DisplayMessage;

public final class DisplayMessageHandler extends MessageHandler<DisplayMessage> {

	@Override
	public void handle(Player player, DisplayMessage message) {
		InterfaceSet interfaces = player.getInterfaceSet();
		DisplayMode currentMode = interfaces.getDisplayMode();
		DisplayMode newMode;
		if (message.getMode() == 0 || message.getMode() == 1)
			newMode = DisplayMode.FIXED;
		else
			newMode = DisplayMode.RESIZABLE;

		if (newMode != currentMode) {
			interfaces.setDisplayMode(newMode);
			interfaces.init();
			interfaces.openWindow(Interface.DISPLAY_SETTINGS); // TODO close on the old root?
		}
	}

}
