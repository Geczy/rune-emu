package net.scapeemulator.game.msg.handler;

import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.MessageHandler;
import net.scapeemulator.game.msg.impl.ChatMessage;

public final class ChatMessageHandler extends MessageHandler<ChatMessage> {

	@Override
	public void handle(Player player, ChatMessage message) {
		player.setChatMessage(message);
	}

}
