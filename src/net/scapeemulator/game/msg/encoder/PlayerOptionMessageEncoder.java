package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.PlayerMenuOptionMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrame.Type;
import net.scapeemulator.game.net.game.GameFrameBuilder;

/**
 * Written by Hadyn Richard
 */
public final class PlayerOptionMessageEncoder extends MessageEncoder<PlayerMenuOptionMessage> {
    
    public PlayerOptionMessageEncoder() {
        super(PlayerMenuOptionMessage.class);
    }

    @Override
    public GameFrame encode(ByteBufAllocator alloc, PlayerMenuOptionMessage message) throws IOException {      
        GameFrameBuilder builder = new GameFrameBuilder(alloc, 44, Type.VARIABLE_BYTE);
        builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, 65535);
        builder.put(DataType.BYTE, message.isAtTop() ? 1 : 0);
        builder.put(DataType.BYTE, message.getId());
        builder.putString(message.getOption());
        return builder.toGameFrame();
    }
}
