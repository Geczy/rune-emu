package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;

import java.io.IOException;

import net.scapeemulator.game.model.Widget;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceNPCHeadMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

/**
 * Written by Hadyn Richard
 */
public final class InterfaceNPCHeadMessageEncoder extends MessageEncoder<InterfaceNPCHeadMessage> {
    
    public InterfaceNPCHeadMessageEncoder() {
        super(InterfaceNPCHeadMessage.class);
    }

    @Override
    public GameFrame encode(ByteBufAllocator alloc, InterfaceNPCHeadMessage message) throws IOException {
        GameFrameBuilder builder = new GameFrameBuilder(alloc, 73);
        builder.put(DataType.SHORT, DataTransformation.ADD, message.getNpcId()); 
        builder.put(DataType.INT, DataOrder.LITTLE, Widget.getHash(message.getId(), message.getComponentId()));
        builder.put(DataType.SHORT, DataOrder.LITTLE, 0);           // Counter
	return builder.toGameFrame();
    }
}