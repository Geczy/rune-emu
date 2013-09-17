package net.scapeemulator.game.update;

import net.scapeemulator.game.msg.impl.PlayerUpdateMessage;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public abstract class PlayerBlock {

	private final int flag;

	public PlayerBlock(int flag) {
		this.flag = flag;
	}

	public int getFlag() {
		return flag;
	}

	public abstract void encode(PlayerUpdateMessage message, GameFrameBuilder builder);

}
