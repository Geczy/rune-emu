package net.scapeemulator.game.update;

import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.impl.PlayerUpdateMessage;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class IdlePlayerDescriptor extends PlayerDescriptor {

	public IdlePlayerDescriptor(Player player, int[] tickets) {
		super(player, tickets);
	}

	@Override
	public void encodeDescriptor(PlayerUpdateMessage message, GameFrameBuilder builder, GameFrameBuilder blockBuilder) {
		if (isBlockUpdatedRequired()) {
			builder.putBits(1, 1);
			builder.putBits(2, 0);
		} else {
			builder.putBits(1, 0);
		}
	}

}
