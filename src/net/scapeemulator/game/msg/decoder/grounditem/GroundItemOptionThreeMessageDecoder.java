package net.scapeemulator.game.msg.decoder.grounditem;

import net.scapeemulator.game.model.Option;
import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.grounditem.GroundItemOptionMessage;
import net.scapeemulator.game.net.game.*;

public final class GroundItemOptionThreeMessageDecoder extends MessageDecoder<GroundItemOptionMessage> {

    public GroundItemOptionThreeMessageDecoder() {
        super(66);
    }

    @Override
    public GroundItemOptionMessage decode(GameFrame frame) {
        GameFrameReader reader = new GameFrameReader(frame);
        int x = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
        int id = (int) reader.getSigned(DataType.SHORT);
        int y = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
        return new GroundItemOptionMessage(id, x, y, Option.THREE);
    }

}
