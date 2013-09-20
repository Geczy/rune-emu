package net.scapeemulator.cache.def;

import java.nio.ByteBuffer;
import net.scapeemulator.cache.util.ByteBufferUtils;

/**
 * Written by Hadyn Richard
 */
public final class NPCDefinition {

    private String name;
    private String[] options;
    private int size;

    public static NPCDefinition decode(ByteBuffer buffer) {
        NPCDefinition def = new NPCDefinition();
        def.options = new String[5];
        while (true) {
            int opcode = buffer.get() & 0xFF;
            if (opcode == 0) {
                break;
            }

            if (opcode == 1) {
                int var0 = buffer.get() & 0xff;
                for (int i = 0; i < var0; i++) {
                    int var1 = buffer.getShort() & 0xffff;
                }
            }

            if (opcode == 2) {
                def.name = ByteBufferUtils.getString(buffer);
            }

            if (opcode == 12) {
                def.size = buffer.get() & 0xff;
            }

            if (opcode >= 30 && opcode <= 35) {
                def.options[opcode - 30] = ByteBufferUtils.getString(buffer);
                if (def.options[opcode - 30].equals("hidden")) {
                    def.options[opcode - 30] = null;
                }
            }

            if (opcode == 40) {
                int var0 = buffer.get() & 0xff;
                for (int i = 0; i < var0; i++) {
                    int var1 = buffer.getShort() & 0xffff;
                    int var2 = buffer.getShort() & 0xffff;
                }
            }

            if (opcode == 41) {
                int var0 = buffer.get() & 0xff;
                for (int i = 0; i < var0; i++) {
                    int var1 = buffer.getShort() & 0xffff;
                    int var2 = buffer.getShort() & 0xffff;
                }
            }

            if (opcode == 42) {
                int var0 = buffer.get() & 0xff;
                for (int i = 0; i < var0; i++) {
                    int var1 = buffer.get();
                }
            }

            if (opcode == 60) {
                int var0 = buffer.get() & 0xff;
                for (int i = 0; i < var0; i++) {
                    int var1 = buffer.getShort() & 0xffff;
                }
            }

            if (opcode == 95) {
                int var0 = buffer.getShort() & 0xffff;
            }

            if (opcode == 97) {
                int var0 = buffer.getShort() & 0xffff;
            }

            if (opcode == 98) {
                int var0 = buffer.getShort() & 0xffff;
            }

            if (opcode == 100) {
                int var0 = buffer.get() & 0xff;
            }

            if (opcode == 101) {
                int var0 = buffer.get() & 0xff;
            }

            if (opcode == 102) {
                int var0 = buffer.getShort() & 0xffff;
            }

            if (opcode == 103) {
                int var0 = buffer.getShort() & 0xffff;
            }

            if (opcode == 106 || opcode == 118) {
                int var0 = buffer.getShort() & 0xffff;
                int var1 = buffer.getShort() & 0xffff;

                if (opcode == 118) {
                    int var3 = buffer.getShort() & 0xffff;
                }

                int var4 = buffer.get() & 0xff;
                for (int i = 0; i <= var4; i++) {
                    int var5 = buffer.getShort() & 0xffff;
                }
            }

            if (opcode == 113) {
                int var0 = buffer.getShort() & 0xffff;
                int var1 = buffer.getShort() & 0xffff;
            }

            if (opcode == 114) {
                int var0 = buffer.get() & 0xff;
                int var1 = buffer.get() & 0xff;
            }

            if (opcode == 115) {
                int var0 = buffer.get() & 0xff;
                int var1 = buffer.get() & 0xff;
            }

            if (opcode == 119) {
                int var0 = buffer.get() & 0xff;
            }

            if (opcode == 121) {
                int var0 = buffer.get() & 0xff;
                for (int i = 0; i < var0; i++) {
                    int var1 = buffer.get() & 0xff;
                    int var2 = buffer.get() & 0xff;
                    int var3 = buffer.get() & 0xff;
                    int var4 = buffer.get() & 0xff;
                }
            }

            if (opcode == 122) {
                int var0 = buffer.getShort() & 0xffff;
            }

            if (opcode == 123) {
                int var0 = buffer.getShort() & 0xffff;
            }

            if (opcode == 125) {
                int var0 = buffer.get();
            }

            if (opcode == 126) {
                int var0 = buffer.getShort() & 0xffff;
            }

            if (opcode == 127) {
                int var0 = buffer.getShort() & 0xffff;
            }

            if (opcode == 128) {
                int var0 = buffer.get() & 0xff;
            }

            if (opcode == 134) {
                int var0 = buffer.getShort() & 0xffff;
                int var1 = buffer.getShort() & 0xffff;
                int var2 = buffer.getShort() & 0xffff;
                int var3 = buffer.getShort() & 0xffff;
                int var4 = buffer.get() & 0xff;
            }

            if (opcode == 135) {
                int var0 = buffer.get() & 0xff;
                int var1 = buffer.getShort() & 0xffff;
            }

            if (opcode == 136) {
                int var0 = buffer.get() & 0xff;
                int var1 = buffer.getShort() & 0xffff;
            }

            if (opcode == 137) {
                int var0 = buffer.getShort() & 0xffff;
            }
            
            if (opcode == 249) {
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
    
    public int getSize() {
        return size;
    }
    
    public String[] getOptions() {
        return options;
    }
}
