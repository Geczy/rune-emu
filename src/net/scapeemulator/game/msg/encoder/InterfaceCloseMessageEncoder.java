package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceCloseMessage;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class InterfaceCloseMessageEncoder extends MessageEncoder<InterfaceCloseMessage> {

	public InterfaceCloseMessageEncoder() {
		super(InterfaceCloseMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, InterfaceCloseMessage message) {
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 149);
		builder.put(DataType.SHORT, 0);
		//builder.put(DataType.SHORT, message.getId());
		//builder.put(DataType.SHORT, message.getSlot());
		builder.put(DataType.INT, (message.getId() << 16) | message.getSlot());
		return builder.toGameFrame();
	}

}
