package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.ConfigMessage;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class ConfigMessageEncoder extends MessageEncoder<ConfigMessage> {

	public ConfigMessageEncoder() {
		super(ConfigMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, ConfigMessage message) throws IOException {
		int id = message.getId();
		int value = message.getValue();

		if (value >= -128 && value <= 127) {
			GameFrameBuilder builder = new GameFrameBuilder(alloc, 60);
			builder.put(DataType.SHORT, DataTransformation.ADD, id);
			builder.put(DataType.BYTE, DataTransformation.NEGATE, value);
			return builder.toGameFrame();
		} else {
			GameFrameBuilder builder = new GameFrameBuilder(alloc, 226);
			builder.put(DataType.INT, value);
			builder.put(DataType.SHORT, DataTransformation.ADD, id);
			return builder.toGameFrame();
		}
	}

}
