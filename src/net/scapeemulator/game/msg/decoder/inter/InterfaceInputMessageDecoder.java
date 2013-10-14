package net.scapeemulator.game.msg.decoder.inter;

import java.io.IOException;

import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceInputMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameReader;

/**
 * Written by Hadyn Richard
 */
public final class InterfaceInputMessageDecoder extends MessageDecoder<InterfaceInputMessage> {
    
    public InterfaceInputMessageDecoder() {
        super(132);
    }

    @Override
    public InterfaceInputMessage decode(GameFrame frame) throws IOException {
        GameFrameReader reader = new GameFrameReader(frame);
        int hash = (int) reader.getUnsigned(DataType.INT, DataOrder.MIDDLE);
        int id = hash >> 16, componentId = hash & 0xffff;
        int dynamicId = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
        return new InterfaceInputMessage(id, componentId, dynamicId);
    }
}
