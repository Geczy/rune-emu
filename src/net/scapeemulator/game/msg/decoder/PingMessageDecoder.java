package net.scapeemulator.game.msg.decoder;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.PingMessage;
import net.scapeemulator.game.net.game.GameFrame;

import java.io.IOException;

public final class PingMessageDecoder extends MessageDecoder<PingMessage> {

	private static final PingMessage PING_MESSAGE = new PingMessage();

	public PingMessageDecoder() {
		super(93);
	}

	@Override
	public PingMessage decode(GameFrame frame) throws IOException {
		return PING_MESSAGE;
	}

}
