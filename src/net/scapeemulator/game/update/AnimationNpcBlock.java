package net.scapeemulator.game.update;

import net.scapeemulator.game.model.mob.Animation;
import net.scapeemulator.game.model.npc.NPC;
import net.scapeemulator.game.msg.impl.NpcUpdateMessage;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class AnimationNpcBlock extends NpcBlock {

	private final Animation animation;

	public AnimationNpcBlock(NPC npc) {
		super(0x10);
		this.animation = npc.getAnimation();
	}

	@Override
	public void encode(NpcUpdateMessage message, GameFrameBuilder builder) {
		builder.put(DataType.SHORT, animation.getId());
		builder.put(DataType.BYTE, animation.getDelay());
	}

}
