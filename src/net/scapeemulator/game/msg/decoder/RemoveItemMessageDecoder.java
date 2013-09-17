package net.scapeemulator.game.msg.decoder;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.RemoveItemMessage;
import net.scapeemulator.game.net.game.*;

import java.io.IOException;

public final class RemoveItemMessageDecoder extends MessageDecoder<RemoveItemMessage> {

	public RemoveItemMessageDecoder() {
		super(81);
	}

	@Override
	public RemoveItemMessage decode(GameFrame frame) throws IOException {
		GameFrameReader reader = new GameFrameReader(frame);
		int itemSlot = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		int itemId = (int) reader.getUnsigned(DataType.SHORT);
		int inter = (int) reader.getSigned(DataType.INT, DataOrder.MIDDLE);
		int id = (inter >> 16) & 0xFFFF;
		int slot = inter & 0xFFFF;
		return new RemoveItemMessage(id, slot, itemSlot, itemId);
	}

}
