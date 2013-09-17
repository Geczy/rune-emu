package net.scapeemulator.game.msg;

import net.scapeemulator.game.msg.Message;
import net.scapeemulator.game.net.game.GameFrame;

import java.io.IOException;

public abstract class MessageDecoder<T extends Message> {

	protected final int opcode;

	public MessageDecoder(int opcode) {
		this.opcode = opcode;
	}

	public abstract T decode(GameFrame frame) throws IOException;

}
