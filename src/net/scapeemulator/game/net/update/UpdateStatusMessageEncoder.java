package net.scapeemulator.game.net.update;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class UpdateStatusMessageEncoder extends MessageToByteEncoder<UpdateStatusMessage> {

	@Override
	public void encode(ChannelHandlerContext ctx, UpdateStatusMessage msg, ByteBuf out) throws Exception {
		out.writeByte(msg.getStatus());
	}

}
