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

package net.scapeemulator.game.msg.decoder.button;

import java.io.IOException;

import net.scapeemulator.game.model.ExtendedOption;
import net.scapeemulator.game.msg.MessageDecoder;
import net.scapeemulator.game.msg.impl.button.ButtonOptionMessage;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameReader;

/**
 * Created by Hadyn Richard
 */
public final class ButtonOptionMessageDecoder extends MessageDecoder<ButtonOptionMessage> {
    
    private final ExtendedOption option;

    public ButtonOptionMessageDecoder(ExtendedOption option, int id) {
        super(id);
        this.option = option;
    }

    @Override
    public ButtonOptionMessage decode(GameFrame frame) throws IOException {
        GameFrameReader reader = new GameFrameReader(frame);
        int hash = (int) reader.getSigned(DataType.INT);
        int param = (int) reader.getSigned(DataType.SHORT);
        return new ButtonOptionMessage(hash, param, option);
    }
}
