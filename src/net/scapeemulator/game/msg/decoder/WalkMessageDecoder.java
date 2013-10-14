package net.scapeemulator.game.msg.decoder;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.WalkMessage;
import net.scapeemulator.game.msg.impl.WalkMessage.Step;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameReader;

public final class WalkMessageDecoder extends MessageDecoder<WalkMessage> {

	public WalkMessageDecoder(int opcode) {
		super(opcode);
	}

	@Override
	public WalkMessage decode(GameFrame frame) throws IOException {
		GameFrameReader reader = new GameFrameReader(frame);

		boolean anticheat = frame.getOpcode() == 39;
		int stepCount = (reader.getLength() - (anticheat ? 19 : 5)) / 2 + 1;
		boolean running = reader.getUnsigned(DataType.BYTE, DataTransformation.ADD) == 1;
		int x = (int) reader.getUnsigned(DataType.SHORT);
		int y = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);

		Step[] steps = new Step[stepCount];
		steps[0] = new Step(x, y);
		for (int i = 1; i < stepCount; i++) {
			int stepX = x + (int) reader.getSigned(DataType.BYTE, DataTransformation.ADD);
			int stepY = y + (int) reader.getSigned(DataType.BYTE, DataTransformation.SUBTRACT);
			steps[i] = new Step(stepX, stepY);
		}

		return new WalkMessage(steps, running);
	}

}
