package net.scapeemulator.game.msg.decoder;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.FocusMessage;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameReader;

public final class FocusMessageDecoder extends MessageDecoder<FocusMessage> {

	private static final FocusMessage FOCUSED_MESSAGE = new FocusMessage(true);
	private static final FocusMessage NOT_FOCUSED_MESSAGE = new FocusMessage(false);

	public FocusMessageDecoder() {
		super(22);
	}

	@Override
	public FocusMessage decode(GameFrame frame) throws IOException {
		GameFrameReader reader = new GameFrameReader(frame);
		int focused = (int) reader.getUnsigned(DataType.BYTE);
		return focused != 0 ? FOCUSED_MESSAGE : NOT_FOCUSED_MESSAGE;
	}

}
