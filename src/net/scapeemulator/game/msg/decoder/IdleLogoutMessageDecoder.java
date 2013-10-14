package net.scapeemulator.game.msg.decoder;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.IdleLogoutMessage;
import net.scapeemulator.game.net.game.GameFrame;

public final class IdleLogoutMessageDecoder extends MessageDecoder<IdleLogoutMessage> {

	private static final IdleLogoutMessage IDLE_LOGOUT_MESSAGE = new IdleLogoutMessage();

	public IdleLogoutMessageDecoder() {
		super(245);
	}

	@Override
	public IdleLogoutMessage decode(GameFrame frame) throws IOException {
		return IDLE_LOGOUT_MESSAGE;
	}

}
