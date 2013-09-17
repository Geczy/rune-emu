package net.scapeemulator.game.net.game;

import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.scapeemulator.game.msg.CodecRepository;
import net.scapeemulator.game.msg.Message;
import net.scapeemulator.game.msg.MessageEncoder;

import java.io.IOException;

public final class GameMessageEncoder extends MessageToMessageEncoder<Message> {

	private final CodecRepository codecs;

	public GameMessageEncoder(CodecRepository codecs) {
		this.codecs = codecs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void encode(ChannelHandlerContext ctx, Message message, MessageBuf<Object> out) throws IOException {
		MessageEncoder<Message> encoder = (MessageEncoder<Message>) codecs.get(message.getClass());
		out.add(encoder.encode(ctx.alloc(), message));
	}

}
