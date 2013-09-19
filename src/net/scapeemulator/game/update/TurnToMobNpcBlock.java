package net.scapeemulator.game.update;

import net.scapeemulator.game.model.npc.NPC;
import net.scapeemulator.game.msg.impl.NpcUpdateMessage;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrameBuilder;

/**
 * Written by Hadyn Richard
 */
public class TurnToMobNpcBlock extends NpcBlock {
    
    private final int turnToTarget;
    
    public TurnToMobNpcBlock(NPC npc) {
        super(0x4);
        turnToTarget = npc.getTurnToTargetId();
    }

    @Override
    public void encode(NpcUpdateMessage message, GameFrameBuilder builder) {
        builder.put(DataType.SHORT, DataTransformation.ADD, turnToTarget);
    }
}
