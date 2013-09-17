package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.ServerMessage;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrame.Type;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class ServerMessageEncoder extends MessageEncoder<ServerMessage> {

	public ServerMessageEncoder() {
		super(ServerMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, ServerMessage message) {
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 70, Type.VARIABLE_BYTE);
		builder.putString(message.getText());
		return builder.toGameFrame();
	}

}
