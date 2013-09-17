package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.ResetMinimapFlagMessage;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

import java.io.IOException;

public final class ResetMinimapFlagMessageEncoder extends MessageEncoder<ResetMinimapFlagMessage> {

	public ResetMinimapFlagMessageEncoder() {
		super(ResetMinimapFlagMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, ResetMinimapFlagMessage message) throws IOException {
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 153);
		return builder.toGameFrame();
	}

}
