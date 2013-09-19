package net.scapeemulator.game.msg.decoder.npc;

import java.io.IOException;
import net.scapeemulator.game.model.Option;
import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.npc.NPCOptionMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameReader;

/**
 * Written by Hadyn Richard
 */
public final class NPCOptionOneMessageDecoder extends MessageDecoder<NPCOptionMessage> {
    
    public NPCOptionOneMessageDecoder() {
        super(78);
    }

    @Override
    public NPCOptionMessage decode(GameFrame frame) throws IOException {
        GameFrameReader reader = new GameFrameReader(frame);
        int id = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
        return new NPCOptionMessage(id, Option.ONE);
    }
}