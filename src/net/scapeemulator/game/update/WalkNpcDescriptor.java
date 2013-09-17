package net.scapeemulator.game.update;

import net.scapeemulator.game.model.mob.Direction;
import net.scapeemulator.game.model.npc.NPC;
import net.scapeemulator.game.msg.impl.NpcUpdateMessage;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class WalkNpcDescriptor extends NpcDescriptor {

	private final Direction direction;

	public WalkNpcDescriptor(NPC npc) {
		super(npc);
		this.direction = npc.getFirstDirection();
	}

	@Override
	public void encodeDescriptor(NpcUpdateMessage message, GameFrameBuilder builder, GameFrameBuilder blockBuilder) {
		builder.putBits(1, 1);
		builder.putBits(2, 1);
		builder.putBits(3, direction.toInteger());
		builder.putBits(1, isBlockUpdatedRequired() ? 1 : 0);
	}

}
