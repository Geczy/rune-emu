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

package net.scapeemulator.cache.def;

import java.nio.ByteBuffer;

/**
 * Created by Hadyn Richard
 */
public final class VarbitDefinition {

    private int state, lowBit, highBit;

    @SuppressWarnings("unused")
    public static VarbitDefinition decode(ByteBuffer buffer) {
        VarbitDefinition def = new VarbitDefinition();
        while (true) {
            int opcode = buffer.get() & 0xFF;
            if (opcode == 0)
                break;
            if(opcode == 1) {
                def.state = buffer.getShort() & 0xffff;
                def.lowBit = buffer.get() & 0xff;
                def.highBit = buffer.get() & 0xff;
            }
        }
        return def;
    }
    
    public int getState() {
        return state;
    }
    
    public int getLowBit() {
        return lowBit;
    }
    
    public int getHighBit() {
        return highBit;
    }
}
