package net.scapeemulator.game.msg.decoder;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.ClickMessage;
import net.scapeemulator.game.net.game.*;

import java.io.IOException;

public final class ClickMessageDecoder extends MessageDecoder<ClickMessage> {

	public ClickMessageDecoder() {
		super(75);
	}

	@Override
	public ClickMessage decode(GameFrame frame) throws IOException {
		GameFrameReader reader = new GameFrameReader(frame);
		int flags = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
		int pos = (int) reader.getUnsigned(DataType.INT, DataOrder.INVERSED_MIDDLE);

		int time = flags & 0x7fff;
		boolean rightClick = ((flags >> 15) & 0x1) != 0;

		int x = pos & 0xffff;
		int y = (pos >> 16) & 0xffff;

		return new ClickMessage(time, x, y, rightClick);
	}

}
