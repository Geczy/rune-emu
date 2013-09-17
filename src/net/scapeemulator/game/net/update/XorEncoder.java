package net.scapeemulator.game.net.update;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToByteEncoder;

public final class XorEncoder extends ByteToByteEncoder {

	private int key = 0;

	public void setKey(int key) {
		this.key = key;
	}

	@Override
	public void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) {
		while (in.isReadable()) {
			out.writeByte(in.readUnsignedByte() ^ key);
		}
	}

}
