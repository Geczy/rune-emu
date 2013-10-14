package net.scapeemulator.game.msg.decoder.object;

import net.scapeemulator.game.model.Option;
import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.object.ObjectOptionMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameReader;

public final class ObjectOptionTwoMessageDecoder extends MessageDecoder<ObjectOptionMessage> {

	public ObjectOptionTwoMessageDecoder() {
		super(194);
	}

	@Override
	public ObjectOptionMessage decode(GameFrame frame) {
		GameFrameReader reader = new GameFrameReader(frame);
		int y = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
		int x = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		int id = (int) reader.getSigned(DataType.SHORT);
		return new ObjectOptionMessage(x, y, id, Option.TWO);
	}

}
