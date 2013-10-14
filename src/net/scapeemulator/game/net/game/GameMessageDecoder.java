package net.scapeemulator.game.net.game;

import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.IOException;

import net.scapeemulator.game.msg.CodecRepository;
import net.scapeemulator.game.msg.Message;
import net.scapeemulator.game.msg.MessageDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GameMessageDecoder extends MessageToMessageDecoder<GameFrame> {

	private static final Logger logger = LoggerFactory.getLogger(GameMessageDecoder.class);

	private final CodecRepository codecs;

	public GameMessageDecoder(CodecRepository codecs) {
		this.codecs = codecs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void decode(ChannelHandlerContext ctx, GameFrame frame, MessageBuf<Object> out) throws IOException {
		MessageDecoder<Message> decoder = (MessageDecoder<Message>) codecs.get(frame.getOpcode());

		if (decoder == null) {
			logger.warn("No decoder for packet id " + frame.getOpcode() + ".");
			return;
		}

		out.add(decoder.decode(frame));
	}

}
