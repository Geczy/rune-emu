package net.scapeemulator.game.msg.decoder.button;

import net.scapeemulator.game.model.ExtendedOption;
import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.button.ButtonOptionMessage;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameReader;

public final class OldButtonMessageDecoder extends MessageDecoder<ButtonOptionMessage> {

	public OldButtonMessageDecoder() {
		super(10);
	}

	@Override
	public ButtonOptionMessage decode(GameFrame frame) {
		GameFrameReader reader = new GameFrameReader(frame);
		int hash = (int) reader.getSigned(DataType.INT);
		return new ButtonOptionMessage(hash, -1, ExtendedOption.ONE);
	}
}
