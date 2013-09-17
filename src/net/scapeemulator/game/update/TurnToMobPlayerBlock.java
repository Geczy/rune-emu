package net.scapeemulator.game.update;

import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.impl.PlayerUpdateMessage;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrameBuilder;

/**
 * Written by Hadyn Richard
 */
public final class TurnToMobPlayerBlock extends PlayerBlock {
    
    private final int turnToTarget;
    
    public TurnToMobPlayerBlock(Player player) {
        super(0x2);
        turnToTarget = player.getTurnToTarget();
    }

    @Override
    public void encode(PlayerUpdateMessage message, GameFrameBuilder builder) {
        builder.put(DataType.SHORT, DataTransformation.ADD, turnToTarget);
    }
}