package net.scapeemulator.game.update;

import net.scapeemulator.game.model.mob.Direction;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.msg.impl.PlayerUpdateMessage;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class AddPlayerDescriptor extends PlayerDescriptor {

	private final int id;
	private final Direction direction;
	private final Position position;

	public AddPlayerDescriptor(Player player, int[] tickets) {
		super(player, tickets);
		this.id = player.getId();
		this.direction = player.getMostRecentDirection();
		this.position = player.getPosition();
	}

	@Override
	public void encodeDescriptor(PlayerUpdateMessage message, GameFrameBuilder builder, GameFrameBuilder blockBuilder) {
		int x = position.getX() - message.getPosition().getX();
		int y = position.getY() - message.getPosition().getY();

		builder.putBits(11, id);
		builder.putBits(1, isBlockUpdatedRequired() ? 1 : 0); // check
		builder.putBits(5, x);
		builder.putBits(3, direction.toInteger());
		builder.putBits(1, 1); // check
		builder.putBits(5, y);
	}

}
