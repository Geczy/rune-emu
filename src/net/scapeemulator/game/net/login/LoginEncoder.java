package net.scapeemulator.game.net.login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class LoginEncoder extends MessageToByteEncoder<LoginResponse> {

	@Override
	public void encode(ChannelHandlerContext ctx, LoginResponse response, ByteBuf buf) {
		buf.writeByte(response.getStatus());
		buf.writeBytes(response.getPayload());

		if (response.getStatus() != LoginResponse.STATUS_EXCHANGE_KEYS)
			ctx.pipeline().remove(this);
	}

}
