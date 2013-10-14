package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceTextMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrame.Type;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class InterfaceTextMessageEncoder extends MessageEncoder<InterfaceTextMessage> {

	public InterfaceTextMessageEncoder() {
		super(InterfaceTextMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, InterfaceTextMessage message) {
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 171, Type.VARIABLE_SHORT);
		builder.put(DataType.INT, DataOrder.INVERSED_MIDDLE, (message.getId() << 16) | message.getSlot());
		builder.putString(message.getText());
		builder.put(DataType.SHORT, DataTransformation.ADD, 0);
		return builder.toGameFrame();
	}

}
