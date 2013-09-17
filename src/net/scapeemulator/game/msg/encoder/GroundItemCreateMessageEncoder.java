/**
 * Copyright (c) 2012, Hadyn Richard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 */

package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.grounditem.GroundItemCreateMessage;
import net.scapeemulator.game.net.game.*;

import java.io.IOException;

/**
 * Created by Hadyn Richard
 */
public final class GroundItemCreateMessageEncoder extends MessageEncoder<GroundItemCreateMessage> {

    public GroundItemCreateMessageEncoder() {
        super(GroundItemCreateMessage.class);
    }

    @Override
    public GameFrame encode(ByteBufAllocator alloc, GroundItemCreateMessage msg) throws IOException {
        GameFrameBuilder builder = new GameFrameBuilder(alloc, 33);
        builder.put(DataType.SHORT, DataOrder.LITTLE, msg.getItemId());
        builder.put(DataType.BYTE, msg.getPosition().blockHash());
        builder.put(DataType.SHORT, DataTransformation.ADD, msg.getAmount());
        return builder.toGameFrame();
    }
}
