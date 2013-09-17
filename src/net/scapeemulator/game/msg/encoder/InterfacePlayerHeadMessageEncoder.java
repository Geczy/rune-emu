package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import net.scapeemulator.game.model.Widget;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.inter.InterfacePlayerHeadMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

/**
 * Written by Hadyn Richard
 */
public final class InterfacePlayerHeadMessageEncoder extends MessageEncoder<InterfacePlayerHeadMessage> {
    
    public InterfacePlayerHeadMessageEncoder() {
        super(InterfacePlayerHeadMessage.class);
    }

    @Override
    public GameFrame encode(ByteBufAllocator alloc, InterfacePlayerHeadMessage message) throws IOException {
        GameFrameBuilder builder = new GameFrameBuilder(alloc, 66);
        builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, 0); //Counter
        builder.put(DataType.INT, DataOrder.MIDDLE, Widget.getHash(message.getId(), message.getComponentId()));
	return builder.toGameFrame();
    }
}