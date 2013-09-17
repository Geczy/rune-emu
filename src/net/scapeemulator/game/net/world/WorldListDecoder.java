package net.scapeemulator.game.net.world;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public final class WorldListDecoder extends ByteToMessageDecoder {

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf buf, MessageBuf<Object> out) {
		if (buf.readableBytes() < 4)
			return;

		out.add(new WorldHandshakeMessage(buf.readInt()));
	}

}
