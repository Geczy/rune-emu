package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.ScriptIntMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class ScriptIntMessageEncoder extends MessageEncoder<ScriptIntMessage> {

	public ScriptIntMessageEncoder() {
		super(ScriptIntMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, ScriptIntMessage message) throws IOException {
		int id = message.getId();
		int value = message.getValue();

		if (value >= -128 && value <= 127) {
			GameFrameBuilder builder = new GameFrameBuilder(alloc, 65);
			builder.put(DataType.SHORT, DataOrder.LITTLE, 0);
			builder.put(DataType.BYTE, DataTransformation.NEGATE, value);
			builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, id);
			return builder.toGameFrame();
		} else {
			GameFrameBuilder builder = new GameFrameBuilder(alloc, 69);
			builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, 0);
			builder.put(DataType.INT, value);
			builder.put(DataType.SHORT, DataTransformation.ADD, id);
			return builder.toGameFrame();
		}
	}

}
