package net.scapeemulator.game.msg;

import io.netty.buffer.ByteBufAllocator;

import java.io.IOException;

import net.scapeemulator.game.net.game.GameFrame;

public abstract class MessageEncoder<T extends Message> {

	protected final Class<T> clazz;

	public MessageEncoder(Class<T> clazz) {
		this.clazz = clazz;
	}

	public abstract GameFrame encode(ByteBufAllocator alloc, T message) throws IOException;

}
