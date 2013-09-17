package net.scapeemulator.game.update;

import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.impl.PlayerUpdateMessage;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class RemovePlayerDescriptor extends PlayerDescriptor {

	public RemovePlayerDescriptor(Player player, int[] tickets) {
		super(player, tickets);
	}

	@Override
	public void encodeDescriptor(PlayerUpdateMessage message, GameFrameBuilder builder, GameFrameBuilder blockBuilder) {
		builder.putBits(1, 1);
		builder.putBits(2, 3);
	}

}
