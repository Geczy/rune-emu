/**
 * Copyright (c) 2012, Hadyn Richard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and permission notice shall be included in 
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
import java.util.Arrays;

/**
 * Created by Hadyn Richard
 */
public final class WidgetDefinition {

    private int hash;


    public static WidgetDefinition decode(ByteBuffer buffer, int hash) {
        WidgetDefinition definition = new WidgetDefinition();
        definition.hash = hash;
        if (buffer.get() == -1) {
            decodeNewFormat(definition, buffer);
        } else {
            decodeOldFormat(definition, buffer);
        }
        
        
        return definition;
    }

    private static void decodeOldFormat(WidgetDefinition definition, ByteBuffer buffer) {

    }

    private static void decodeNewFormat(WidgetDefinition def, ByteBuffer buffer) {
        int type = buffer.get() & 0xff;
        if (0 != (128 & type)) {
            type &= 127;
            ByteBufferUtils.getString(buffer);
        }

        int contentType = buffer.getShort() & 0xffff;
        int x = buffer.getShort();
        int y = buffer.getShort();
        int width = buffer.getShort() & 0xffff;
        int height = buffer.getShort() & 0xffff;
        int widthOffset2 = buffer.get();
        int aByte241 = buffer.get();
        int aByte273 = buffer.get();
        int aByte162 = buffer.get();
        int parentId = buffer.getShort() & 0xffff;
        if (65535 == parentId) {
            parentId = -1;
        } else {
            parentId = (def.hash & -65536) + parentId;
        }

        boolean isHidden = buffer.get() == 1;
        if (type == 0) {
            int anInt240 = buffer.getShort() & 0xffff;
            int scrollMaxV = buffer.getShort() & 0xffff;
            boolean aBoolean219 = buffer.get() == 1;
        }

        int optionMask;
        if (type == 5) {
            int disabledImage = buffer.getInt();
            int anInt301 = buffer.getShort() & 0xffff;
            optionMask = buffer.get() & 0xff;
            boolean aBoolean157 = -1 != ~(2 & optionMask);
            boolean aBoolean186 = ~(1 & optionMask) != -1;
            int anInt223 = buffer.get() & 0xff;
            int anInt288 = buffer.get() & 0xff;
            int anInt287 = buffer.getInt();
            boolean aBoolean178 = buffer.get() == 1;
            boolean aBoolean199 = buffer.get() == 1;
        }

        if (type == 6) {
            int anInt202 = 1;
            int anInt201 = buffer.getShort() & 0xffff;
            if (anInt201 == 65535){
                anInt201 = -1;
            }

            int anInt259 = buffer.getShort();
            int anInt230 = buffer.getShort();
            int anInt182 = buffer.getShort() & 0xffff;
            int anInt308 = buffer.getShort() & 0xffff;
            int anInt280 = buffer.getShort() & 0xffff;
            int anInt164 = buffer.getShort() & 0xffff;
            int anInt305 = buffer.getShort() & 0xffff;
            if ('\uffff' == anInt305){
                anInt305 = -1;
            }

            boolean aBoolean181 = buffer.get() == 1;
            short aShort293 = (short) (buffer.getShort() & 0xffff);
            short aShort169 = (short) (buffer.getShort() & 0xffff);
            boolean aBoolean309 = buffer.get() == 1;
            if (widthOffset2 != 0) {
                int anInt184 = buffer.getShort() & 0xffff;
            }

            if (aByte241 != 0) {
                int anInt312 = buffer.getShort() & 0xffff;
            }
        }

        if (~type == -5) {
            int font = buffer.getShort() & 0xffff;
            if (~font == -65536) {
                font = -1;
            }

            String disabledText = ByteBufferUtils.getString(buffer);
            int anInt205 = buffer.get() & 0xff;
            int textAlignment = buffer.get() & 0xff;
            int anInt225 = buffer.get() & 0xff;
            boolean shaded = buffer.get() == 1;
            int disabledColor = buffer.getInt();
        }

        if (type == 3) {
            int disabledColor = buffer.getInt();
            boolean filled = 1 == buffer.get();
            int anInt223 = buffer.get() & 0xff;
        }

        if (-10 == ~type) {
            int anInt250 = buffer.get() & 0xff;
            int disabledColor = buffer.getInt();
            boolean aBoolean167 = 1 == buffer.get();
        }

        optionMask = ByteBufferUtils.getTriByte(buffer);
        int var4 = buffer.get() & 0xff;
        int var5;
        if (var4 != 0) {
            int[] anIntArray299 = new int[10];
            byte[] aByteArray263 = new byte[10];

            for (byte[] aByteArray231 = new byte[10]; var4 != 0; var4 = buffer.get() & 0xff) {
                var5 = (var4 >> 4) - 1;
                var4 = buffer.get() & 0xff | var4 << 8;
                var4 &= 4095;
                if (4095 == var4) {
                    anIntArray299[var5] = -1;
                } else {
                    anIntArray299[var5] = var4;
                }

                aByteArray263[var5] = buffer.get();
                aByteArray231[var5] = buffer.get();
            }
        }

        String aClass94_277 = ByteBufferUtils.getString(buffer);
        var5 = buffer.get() & 0xff;
        int var6 = var5 & 15;
        if (0 < var6) {
            String[] itemOptions = new String[var6];

            for (int var8 = 0; var6 > var8; ++var8) {
                itemOptions[var8] = null;
            }
        }

        int[] anIntArray249 = null;
        int var7 = var5 >> 4;
        if (var7 > 0) {
            int var8 = buffer.get() & 0xff;
            anIntArray249 = new int[var8 + 1];

            for (int var9 = 0; var9 < anIntArray249.length; ++var9) {
                anIntArray249[var9] = -1;
            }

            anIntArray249[var8] = buffer.getShort() & 0xffff;
        }

        if (var7 > 1) {
            int var8 = buffer.get() & 0xff;
            anIntArray249[var8] = buffer.getShort() & 0xffff;
        }

        int anInt214 = buffer.get() & 0xff;
        int anInt179 = buffer.get() & 0xff;
        boolean aBoolean200 = buffer.get() == 1;
        String selectedActionName = ByteBufferUtils.getString(buffer);
        if (0 != (127 & optionMask >> 11)) {
            int var8 = buffer.getShort() & 0xffff;
            int anInt266 = buffer.getShort() & 0xffff;
            if (-65536 == ~var8) {
                var8 = -1;
            }

            if ('\uffff' == anInt266) {
                anInt266 = -1;
            }

            int anInt238 = buffer.getShort() & 0xffff;
            if (anInt238 == '\uffff') {
                anInt238 = -1;
            }
        }

        /* Decode the script params */
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
        decodeScriptParams(buffer);
    }

    private static Object[] decodeScriptParams(ByteBuffer buffer) {
        int amountParams = buffer.get() & 0xff;
        if (amountParams != 0) {
            Object[] params = new Object[amountParams];
            for (int var5 = 0; var5 < amountParams; ++var5) {
                int type = buffer.get() & 0xff;
                if (type != 0) {
                    if (type == 1) {
                        params[var5] = ByteBufferUtils.getString(buffer);
                    }
                } else {
                    params[var5] = buffer.getInt();
                }
            }           
            return params;
        }
        return null;
    }
}
