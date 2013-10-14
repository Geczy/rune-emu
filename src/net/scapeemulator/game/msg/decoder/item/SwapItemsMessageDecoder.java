package net.scapeemulator.game.msg.decoder.item;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.SwapItemsMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameReader;

public final class SwapItemsMessageDecoder extends MessageDecoder<SwapItemsMessage> {

	public SwapItemsMessageDecoder() {
		super(231);
	}

	@Override
	public SwapItemsMessage decode(GameFrame frame) throws IOException {
		GameFrameReader reader = new GameFrameReader(frame);
		int source = (int) reader.getUnsigned(DataType.SHORT);
		int inter = (int) reader.getSigned(DataType.INT, DataOrder.LITTLE);
		int id = (inter >> 16) & 0xFFFF;
		int slot = inter & 0xFFFF;
		int destination = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		int type = (int) reader.getUnsigned(DataType.BYTE, DataTransformation.SUBTRACT);
		return new SwapItemsMessage(id, slot, source, destination, type);
	}

}
