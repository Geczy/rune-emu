package net.scapeemulator.game.update;

import net.scapeemulator.game.msg.impl.NpcUpdateMessage;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public abstract class NpcBlock {

	private final int flag;

	public NpcBlock(int flag) {
		this.flag = flag;
	}

	public int getFlag() {
		return flag;
	}

	public abstract void encode(NpcUpdateMessage message, GameFrameBuilder builder);

}
