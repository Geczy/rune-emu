package net.scapeemulator.game.net.game;

import io.netty.buffer.ByteBuf;

public final class GameFrame {

	public enum Type {
		RAW, FIXED, VARIABLE_BYTE, VARIABLE_SHORT;
	}

	private final int opcode;
	private final Type type;
	private final ByteBuf payload;

	public GameFrame(int opcode, Type type, ByteBuf payload) {
		if (type == Type.RAW)
			throw new IllegalArgumentException();

		this.opcode = opcode;
		this.type = type;
		this.payload = payload;
	}

	public int getOpcode() {
		return opcode;
	}

	public Type getType() {
		return type;
	}

	public ByteBuf getPayload() {
		return payload;
	}

}
