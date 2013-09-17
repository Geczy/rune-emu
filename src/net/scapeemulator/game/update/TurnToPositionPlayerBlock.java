package net.scapeemulator.game.update;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.impl.PlayerUpdateMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrameBuilder;

/**
 * Written by Hadyn Richard
 */
public class TurnToPositionPlayerBlock extends PlayerBlock {

    private final Position position;

    public TurnToPositionPlayerBlock(Player player) {
        super(0x40);
        this.position = player.getTurnToPosition();
    }

    @Override
    public void encode(PlayerUpdateMessage message, GameFrameBuilder builder) {
        // The measure is in half tiles, you must add a half tile because the player is located on the middle of a tile
        builder.put(DataType.SHORT, position.getX() * 2 + 1);
        builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, position.getY() * 2 + 1);
    }
}
