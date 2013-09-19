package net.scapeemulator.game.update;

import net.scapeemulator.game.model.mob.Direction;
import net.scapeemulator.game.model.npc.NPC;
import net.scapeemulator.game.msg.impl.NpcUpdateMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrameBuilder;

import java.util.HashMap;
import java.util.Map;

public abstract class NpcDescriptor {

	public static NpcDescriptor create(NPC npc) {
		Direction firstDirection = npc.getFirstDirection();
		Direction secondDirection = npc.getSecondDirection();

		if (firstDirection == Direction.NONE)
			return new IdleNpcDescriptor(npc);
		else if (secondDirection == Direction.NONE)
			return new WalkNpcDescriptor(npc);
		else
			return new RunNpcDescriptor(npc);
	}

	private final Map<Class<? extends NpcBlock>, NpcBlock> blocks = new HashMap<>();

	public NpcDescriptor(NPC npc) {
		if (npc.isAnimationUpdated())
			addBlock(new AnimationNpcBlock(npc));

		if (npc.isSpotAnimationUpdated())
			addBlock(new SpotAnimationNpcBlock(npc));
                
                if(npc.isTurnToTargetUpdated())
                    addBlock(new TurnToMobNpcBlock(npc));
	}

	private void addBlock(NpcBlock block) {
		blocks.put(block.getClass(), block);
	}

	public boolean isBlockUpdatedRequired() {
		return !blocks.isEmpty();
	}

	public void encode(NpcUpdateMessage message, GameFrameBuilder builder, GameFrameBuilder blockBuilder) {
		encodeDescriptor(message, builder, blockBuilder);

		if (isBlockUpdatedRequired()) {
			int flags = 0;
			for (NpcBlock block : blocks.values())
				flags |= block.getFlag();

			if (flags > 0xFF) {
				flags |= 0x8;
				blockBuilder.put(DataType.SHORT, DataOrder.LITTLE, flags);
			} else {
				blockBuilder.put(DataType.BYTE, flags);
			}

			encodeBlock(message, blockBuilder, AnimationNpcBlock.class);
                        encodeBlock(message, blockBuilder, TurnToMobNpcBlock.class);
			encodeBlock(message, blockBuilder, SpotAnimationNpcBlock.class);
		}
	}

	private void encodeBlock(NpcUpdateMessage message, GameFrameBuilder builder, Class<? extends NpcBlock> type) {
		NpcBlock block = blocks.get(type);
		if (block != null)
			block.encode(message, builder);
	}

	public abstract void encodeDescriptor(NpcUpdateMessage message, GameFrameBuilder builder, GameFrameBuilder blockBuilder);

}
