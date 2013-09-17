package net.scapeemulator.game.net.register;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class RegisterEncoder extends MessageToByteEncoder<RegisterResponse> {

	@Override
	public void encode(ChannelHandlerContext ctx, RegisterResponse response, ByteBuf buf) throws Exception {
		buf.writeByte(response.getStatus());
		buf.writeBytes(response.getPayload());
	}

}
