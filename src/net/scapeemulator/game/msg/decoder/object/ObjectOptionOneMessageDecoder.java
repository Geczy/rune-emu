package net.scapeemulator.game.msg.decoder.object;

import net.scapeemulator.game.model.Option;
import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.object.ObjectOptionMessage;
import net.scapeemulator.game.net.game.*;

public final class ObjectOptionOneMessageDecoder extends MessageDecoder<ObjectOptionMessage> {

	public ObjectOptionOneMessageDecoder() {
		super(254);
	}

	@Override
	public ObjectOptionMessage decode(GameFrame frame) {
		GameFrameReader reader = new GameFrameReader(frame);
		int x = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		int id = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		int y = (int) reader.getUnsigned(DataType.SHORT);
		return new ObjectOptionMessage(id, x, y, Option.ONE);
	}

}
