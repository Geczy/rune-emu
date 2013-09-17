package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.LogoutMessage;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class LogoutMessageEncoder extends MessageEncoder<LogoutMessage> {

	public LogoutMessageEncoder() {
		super(LogoutMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, LogoutMessage message) {
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 86);
		return builder.toGameFrame();
	}

}
