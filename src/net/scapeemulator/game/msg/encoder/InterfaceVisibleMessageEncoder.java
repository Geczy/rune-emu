package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceVisibleMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class InterfaceVisibleMessageEncoder extends MessageEncoder<InterfaceVisibleMessage> {

	public InterfaceVisibleMessageEncoder() {
		super(InterfaceVisibleMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, InterfaceVisibleMessage message) {
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 21);
		builder.put(DataType.BYTE, DataTransformation.NEGATE, message.isVisible() ? 0 : 1);
		builder.put(DataType.SHORT, 0);
		builder.put(DataType.INT, DataOrder.LITTLE, (message.getId() << 16) | message.getSlot());
		return builder.toGameFrame();
	}

}
