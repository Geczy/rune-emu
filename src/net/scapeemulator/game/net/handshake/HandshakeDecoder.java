package net.scapeemulator.game.net.handshake;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundByteHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import net.scapeemulator.game.net.auto.AutoLoginDecoder;
import net.scapeemulator.game.net.jaggrab.JaggrabDecoder;
import net.scapeemulator.game.net.login.LoginDecoder;
import net.scapeemulator.game.net.login.LoginEncoder;
import net.scapeemulator.game.net.register.RegisterDecoder;
import net.scapeemulator.game.net.register.RegisterEncoder;
import net.scapeemulator.game.net.update.FileResponseEncoder;
import net.scapeemulator.game.net.update.UpdateDecoder;
import net.scapeemulator.game.net.update.UpdateStatusMessageEncoder;
import net.scapeemulator.game.net.update.XorEncoder;
import net.scapeemulator.game.net.world.WorldListDecoder;
import net.scapeemulator.game.net.world.WorldListEncoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class HandshakeDecoder extends ChannelInboundByteHandlerAdapter {

	@Override
	public void inboundBufferUpdated(ChannelHandlerContext ctx, ByteBuf buf) throws IOException {
		if (!buf.isReadable())
			return;

		int service = buf.readUnsignedByte();
		ByteBuf additionalBuf = null;
		if (buf.isReadable()) {
			additionalBuf = buf.readBytes(buf.readableBytes());
		}

		ChannelPipeline pipeline = ctx.pipeline();
		pipeline.remove(HandshakeDecoder.class);

		switch (service) {
		case HandshakeMessage.SERVICE_LOGIN:
			pipeline.addFirst(
				new LoginEncoder(),
				new LoginDecoder());
			break;

		case HandshakeMessage.SERVICE_UPDATE:
			pipeline.addFirst(
				new FileResponseEncoder(),
				new UpdateStatusMessageEncoder(),
				new XorEncoder(),
				new UpdateDecoder());
			break;

		case HandshakeMessage.SERVICE_JAGGRAB:
			pipeline.addFirst(
				new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter()),
				new StringDecoder(StandardCharsets.ISO_8859_1),
				new JaggrabDecoder());
			break;

		case HandshakeMessage.SERVICE_REGISTER_PERSONAL_DETAILS:
		case HandshakeMessage.SERVICE_REGISTER_USERNAME:
		case HandshakeMessage.SERVICE_REGISTER_COMMIT:
			pipeline.addFirst(
				new RegisterEncoder(),
				new RegisterDecoder(service));
			break;

		case HandshakeMessage.SERVICE_AUTO_LOGIN:
			pipeline.addFirst(
				new LoginEncoder(),
				new AutoLoginDecoder());
			break;

		case HandshakeMessage.SERVICE_WORLD_LIST:
			pipeline.addFirst(
				new WorldListEncoder(),
				new WorldListDecoder());
			break;

		default:
			throw new IOException("Invalid service id: " + service + ".");
		}

		ctx.nextInboundMessageBuffer().add(new HandshakeMessage(service));
		ctx.fireInboundBufferUpdated();

		if (additionalBuf != null) {
			ChannelHandlerContext head = ctx.pipeline().firstContext();
			head.nextInboundByteBuffer().writeBytes(additionalBuf);
			head.fireInboundBufferUpdated();
		}
	}

}
