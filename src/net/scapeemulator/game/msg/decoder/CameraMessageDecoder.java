package net.scapeemulator.game.msg.decoder;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.CameraMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameReader;

public final class CameraMessageDecoder extends MessageDecoder<CameraMessage> {

	public CameraMessageDecoder() {
		super(21);
	}

	@Override
	public CameraMessage decode(GameFrame frame) throws IOException {
		GameFrameReader reader = new GameFrameReader(frame);
		int pitch = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		int yaw = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		return new CameraMessage(yaw, pitch);
	}

}
