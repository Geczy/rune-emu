package net.scapeemulator.game.update;

import net.scapeemulator.game.model.SpotAnimation;
import net.scapeemulator.game.model.npc.NPC;
import net.scapeemulator.game.msg.impl.NpcUpdateMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class SpotAnimationNpcBlock extends NpcBlock {

	private final SpotAnimation spotAnimation;

	public SpotAnimationNpcBlock(NPC npc) {
		super(0x80);
		this.spotAnimation = npc.getSpotAnimation();
	}

	@Override
	public void encode(NpcUpdateMessage message, GameFrameBuilder builder) {
		builder.put(DataType.SHORT, DataTransformation.ADD, spotAnimation.getId());
		builder.put(DataType.INT, DataOrder.LITTLE, (spotAnimation.getHeight() << 16) | spotAnimation.getDelay());
	}

}
