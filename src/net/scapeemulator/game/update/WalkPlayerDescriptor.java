package net.scapeemulator.game.update;

import net.scapeemulator.game.model.mob.Direction;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.impl.PlayerUpdateMessage;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class WalkPlayerDescriptor extends PlayerDescriptor {

	private final Direction direction;

	public WalkPlayerDescriptor(Player player, int[] tickets) {
		super(player, tickets);
		this.direction = player.getFirstDirection();
	}

	@Override
	public void encodeDescriptor(PlayerUpdateMessage message, GameFrameBuilder builder, GameFrameBuilder blockBuilder) {
		builder.putBits(1, 1);
		builder.putBits(2, 1);
		builder.putBits(3, direction.toInteger());
		builder.putBits(1, isBlockUpdatedRequired() ? 1 : 0);
	}

}
