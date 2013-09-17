package net.scapeemulator.game.net.game;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.scapeemulator.game.net.game.GameFrame.Type;
import net.scapeemulator.util.crypto.StreamCipher;

public final class GameFrameEncoder extends MessageToByteEncoder<GameFrame> {

	private final StreamCipher cipher;

	public GameFrameEncoder(StreamCipher cipher) {
		this.cipher = cipher;
	}

	@Override
	public void encode(ChannelHandlerContext ctx, GameFrame frame, ByteBuf buf) throws Exception {
		Type type = frame.getType();
		ByteBuf payload = frame.getPayload();

		buf.writeByte(frame.getOpcode() + cipher.nextInt());
		if (type == Type.VARIABLE_BYTE)
			buf.writeByte(payload.readableBytes());
		else if (type == Type.VARIABLE_SHORT)
			buf.writeShort(payload.readableBytes());

		buf.writeBytes(payload);
	}

}
