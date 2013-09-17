package net.scapeemulator.game.msg.decoder.inter;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceClosedMessage;
import net.scapeemulator.game.net.game.GameFrame;

import java.io.IOException;

public final class InterfaceClosedMessageDecoder extends MessageDecoder<InterfaceClosedMessage> {

	private static final InterfaceClosedMessage INTERFACE_CLOSED_MESSAGE = new InterfaceClosedMessage();

	public InterfaceClosedMessageDecoder() {
		super(184);
	}

	@Override
	public InterfaceClosedMessage decode(GameFrame frame) throws IOException {
		return INTERFACE_CLOSED_MESSAGE;
	}
}
