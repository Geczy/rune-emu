package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceResetItemsMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class InterfaceResetItemsMessageEncoder extends MessageEncoder<InterfaceResetItemsMessage> {

	public InterfaceResetItemsMessageEncoder() {
		super(InterfaceResetItemsMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, InterfaceResetItemsMessage message) throws IOException {
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 144);
		builder.put(DataType.INT, DataOrder.INVERSED_MIDDLE, (message.getId() << 16) | message.getSlot());
		return builder.toGameFrame();
	}

}
