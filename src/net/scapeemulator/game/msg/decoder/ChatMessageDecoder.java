package net.scapeemulator.game.msg.decoder;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.ChatMessage;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameReader;
import net.scapeemulator.util.ChatUtils;

public final class ChatMessageDecoder extends MessageDecoder<ChatMessage> {

	public ChatMessageDecoder() {
		super(237);
	}

	@Override
	public ChatMessage decode(GameFrame frame) throws IOException {
		GameFrameReader reader = new GameFrameReader(frame);
		int size = reader.getLength() - 2;

		int color = (int) reader.getUnsigned(DataType.BYTE);
		int effects = (int) reader.getUnsigned(DataType.BYTE);

		byte[] bytes = new byte[size];
		reader.getBytes(bytes);
		String text = ChatUtils.unpack(bytes);

		return new ChatMessage(color, effects, text);
	}

}
