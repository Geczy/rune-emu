package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceOpenMessage;
import net.scapeemulator.game.net.game.*;

public final class InterfaceOpenMessageEncoder extends MessageEncoder<InterfaceOpenMessage> {

	public InterfaceOpenMessageEncoder() {
		super(InterfaceOpenMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, InterfaceOpenMessage message) {
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 155);
		builder.put(DataType.BYTE, message.getMode());
		builder.put(DataType.INT, DataOrder.INVERSED_MIDDLE, (message.getId() << 16) | message.getSlot());
		builder.put(DataType.SHORT, DataTransformation.ADD, 0);
		builder.put(DataType.SHORT, message.getChildId());
		return builder.toGameFrame();
	}

}
