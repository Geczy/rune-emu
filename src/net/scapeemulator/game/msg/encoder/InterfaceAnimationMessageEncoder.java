package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import net.scapeemulator.game.model.Widget;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceAnimationMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

/**
 * Written by Hadyn Richard
 */
public final class InterfaceAnimationMessageEncoder extends MessageEncoder<InterfaceAnimationMessage> {
    
    public InterfaceAnimationMessageEncoder() {
        super(InterfaceAnimationMessage.class);
    }

    @Override
    public GameFrame encode(ByteBufAllocator alloc, InterfaceAnimationMessage message) throws IOException {
        GameFrameBuilder builder = new GameFrameBuilder(alloc, 36);
        builder.put(DataType.INT, DataOrder.INVERSED_MIDDLE, Widget.getHash(message.getId(), message.getComponentId()));
        builder.put(DataType.SHORT, DataOrder.LITTLE, message.getAnimationId());
        builder.put(DataType.SHORT, DataTransformation.ADD, 0);                     // Count
        return builder.toGameFrame();
    }
}