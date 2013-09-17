package net.scapeemulator.game.msg.decoder;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.SequenceNumberMessage;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameReader;

import java.io.IOException;

public final class SequenceNumberMessageDecoder extends MessageDecoder<SequenceNumberMessage> {

	public SequenceNumberMessageDecoder() {
		super(177);
	}

	@Override
	public SequenceNumberMessage decode(GameFrame frame) throws IOException {
		GameFrameReader reader = new GameFrameReader(frame);
		int sequenceNumber = (int) reader.getUnsigned(DataType.SHORT);
		return new SequenceNumberMessage(sequenceNumber);
	}

}
