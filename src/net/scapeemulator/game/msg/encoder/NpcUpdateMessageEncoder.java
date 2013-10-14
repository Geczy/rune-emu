package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.NpcUpdateMessage;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;
import net.scapeemulator.game.update.NpcDescriptor;

public final class NpcUpdateMessageEncoder extends MessageEncoder<NpcUpdateMessage> {

	public NpcUpdateMessageEncoder() {
		super(NpcUpdateMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, NpcUpdateMessage message) throws IOException {
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 32, GameFrame.Type.VARIABLE_SHORT);
		GameFrameBuilder blockBuilder = new GameFrameBuilder(alloc);
		builder.switchToBitAccess();

		builder.putBits(8, message.getLocalNpcCount());

		for (NpcDescriptor descriptor : message.getDescriptors())
			descriptor.encode(message, builder, blockBuilder);

		if (blockBuilder.getLength() > 0) {
			builder.putBits(15, 32767);
			builder.switchToByteAccess();
			builder.putRawBuilder(blockBuilder);
		} else {
			builder.switchToByteAccess();
		}

		return builder.toGameFrame();
	}

}
