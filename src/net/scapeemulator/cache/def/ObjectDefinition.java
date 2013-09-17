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

import net.scapeemulator.cache.util.ByteBufferUtils;

import java.nio.ByteBuffer;

/**
 * Created by Hadyn Richard
 */
public final class ObjectDefinition {
    
    private String name;
    
    private String[] options;

    private int width;
    private int length;

    private boolean impenetrable;
    private boolean solid;

    @SuppressWarnings("unused")
    public static ObjectDefinition decode(ByteBuffer buffer) {
        ObjectDefinition def = new ObjectDefinition();
        def.name = "null";
        def.width = 1;
        def.length = 1;
        def.options = new String[5];
        def.impenetrable = true;
        def.solid = true;
        while (true) {
            int opcode = buffer.get() & 0xFF;
            if (opcode == 0)
                break;
            if(opcode == 1) {
                int i = buffer.get() & 0xff;
                if(i > 0) {
                    for(int var5 = 0; var5 < i; var5++) {
                        buffer.getShort();
                        buffer.get();
                    }
                }
            } else if (opcode == 2) {
                def.name = ByteBufferUtils.getJagexString(buffer);
            } else if (opcode == 5) {
                int i = buffer.get() & 0xff;
                if(i > 0) {
                    for(int var5 = 0; var5 < i; var5++) {
                        buffer.getShort();
                    }
                }
            } else if (opcode == 14) {
                def.width = buffer.get() & 0xff;
            } else if (opcode == 15) {
                def.length = buffer.get() & 0xff;
            } else if(opcode == 17) {
                def.impenetrable = false;
                def.solid = false;
            } else if(opcode == 17) {
                def.impenetrable = false;
            } else if (opcode == 19) {
                int i = buffer.get();
            } else if (opcode == 24) {
                int i = buffer.getShort();
            } else if (opcode == 28) {
                int i = buffer.get();
            } else if (opcode == 29) {
                int i = buffer.get();
            } else if (opcode >= 30 && opcode < 35)
                def.options[opcode-30] = ByteBufferUtils.getJagexString(buffer);
            else if (opcode == 39) {
                int i = buffer.get();
            } else if (opcode == 40) {
                int length = buffer.get() & 0xFF;
                for(int index = 0; index < length; index++) {
                    buffer.getShort();
                    buffer.getShort();
                }
            } else if (opcode == 41) {
                int length = buffer.get() & 0xFF;
                for(int index = 0; index < length; index++) {
                    int i = buffer.getShort() & 0xFFFFF;
                    int i2 = buffer.getShort() & 0xFFFFF;
                }
            } else if (opcode == 42) {
                int length = buffer.get() & 0xFF;
                for(int index = 0; index < length; index++) {
                    int i = buffer.get();
                }
            } else if(opcode == 60) {
                int i = buffer.getShort() & 0xffff;
            } else if(opcode == 65) {
                int i = buffer.getShort() & 0xffff;
            } else if(opcode == 66) {
                int i = buffer.getShort() & 0xffff;
            } else if(opcode == 67) {
                int i = buffer.getShort() & 0xffff;
            } else if(opcode == 69) {
                int i = buffer.get() & 0xff;
            } else if(opcode == 70) {
                int i = buffer.getShort();
            } else if(opcode == 71) {
                int i = buffer.getShort();
            } else if(opcode == 72) {
                int i = buffer.getShort();
            } else if(opcode == 75) {
                int i = buffer.get();
            } else if(opcode == 77 || opcode == 92) {
                int i = buffer.getShort() & 0xffff;
                int i2 = buffer.getShort() & 0xffff;

                if(opcode == 92) {
                    int i3 = buffer.getShort();
                }

                int i4 = buffer.get() & 0xff;

                for(int var6 = 0; var6 <= i4; var6++) {
                    int i5 = buffer.getShort();
                }
            } else if (opcode == 78) {
                buffer.getShort();
                buffer.get();
            } else if (opcode == 79) {
                int i = buffer.getShort() & 0xffff;
                int i2 = buffer.getShort() & 0xffff;
                int i3 = buffer.get() & 0xff;
                int i4 = buffer.get() & 0xff;
                for(int counter = 0; counter < i4; ++counter) {
                    int i5 = buffer.getShort() & 0xffff;
                }
            } else if(opcode == 81) {
                int i = buffer.get() & 0xff;
            } else if (opcode == 93){
                int i = buffer.getShort() & 0xFFFFF;
            } else if (opcode == 99) {
                int i = buffer.get() & 0xff;
                int i2 = buffer.getShort() & 0xffff;
            } else if (opcode == 100) {
                int i = buffer.get() & 0xff;
                int i2 = buffer.getShort() & 0xffff;
            } else if (opcode == 101) {
                int i = buffer.get() & 0xff;
            } else if (opcode == 102){
                int i = buffer.getShort() & 0xFFFFF;
            } else if (opcode == 249) {
                int length = buffer.get() & 0xFF;
                for (int index = 0; index < length; index++) {
                    boolean stringInstance = buffer.get() == 1;
                    int key = ByteBufferUtils.getTriByte(buffer);
                    Object value = stringInstance ? ByteBufferUtils.getJagexString(buffer) : buffer.getInt();
                }
            }
        }
        return def;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean hasOptions() {
        for(int i = 0; i < options.length; i++) {
            if(options[i] != null) {
                return true;
            }
        }
        return false;
    }
    
    public String[] getOptions() {
        return options;
    }

    public boolean isImpenetrable() {
        return impenetrable;
    }

    public boolean isSolid() {
        return solid;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getLength() {
        return length;
    }
}