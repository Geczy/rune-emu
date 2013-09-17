package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceAccessMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

/**
 * Written by Hadyn Richard
 */
public class InterfaceAccessMessageEncoder extends MessageEncoder<InterfaceAccessMessage> {
    
    public InterfaceAccessMessageEncoder() {
        super(InterfaceAccessMessage.class);
    }

    @Override
    public GameFrame encode(ByteBufAllocator alloc, InterfaceAccessMessage message) throws IOException {
        GameFrameBuilder builder = new GameFrameBuilder(alloc, 165);
        builder.put(DataType.SHORT, DataOrder.LITTLE, 0);                       // Counter
        builder.put(DataType.SHORT, DataOrder.LITTLE, message.getChildEnd());
        builder.put(DataType.INT, message.getId() << 16 | message.getComponentId());
        builder.put(DataType.SHORT, DataTransformation.ADD, message.getChildStart());
        builder.put(DataType.INT, DataOrder.MIDDLE, message.getFlags());
	return builder.toGameFrame();
    }
}
