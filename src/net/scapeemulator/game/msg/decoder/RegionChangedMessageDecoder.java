package net.scapeemulator.game.msg.decoder;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.RegionChangedMessage;
import net.scapeemulator.game.net.game.GameFrame;

public final class RegionChangedMessageDecoder extends MessageDecoder<RegionChangedMessage> {

	private static final RegionChangedMessage REGION_CHANGED_MESSAGE = new RegionChangedMessage();

	public RegionChangedMessageDecoder() {
		super(110);
	}

	@Override
	public RegionChangedMessage decode(GameFrame frame) throws IOException {
		return REGION_CHANGED_MESSAGE;
	}

}
