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

package net.scapeemulator.game.model.definition;

import net.scapeemulator.cache.Archive;
import net.scapeemulator.cache.Cache;
import net.scapeemulator.cache.Container;
import net.scapeemulator.cache.ReferenceTable;
import net.scapeemulator.cache.ReferenceTable.ChildEntry;
import net.scapeemulator.cache.def.WidgetDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Hadyn Richard
 */
public class WidgetDefinitions {

    private static final Logger logger = LoggerFactory.getLogger(WidgetDefinitions.class);
    private static WidgetDefinition[][] definitions;

    public static void init(Cache cache) throws IOException {
        int count = 0;

        Container tableContainer = Container.decode(cache.getStore().read(255, 3));
        ReferenceTable table = ReferenceTable.decode(tableContainer.getData());

        int files = table.capacity();
        definitions = new WidgetDefinition[files][];

        for (int file = 0; file < files; file++) {
            ReferenceTable.Entry entry = table.getEntry(file);
            if (entry == null)
                continue;

            int members = entry.capacity();
            definitions[file] = new WidgetDefinition[members];

            Archive archive = Archive.decode(cache.read(3, file).getData(), entry.size());
            for (int member = 0; member < entry.capacity(); member++) {
                ChildEntry childEntry = entry.getEntry(member);
                if (childEntry == null)
                    continue;

                ByteBuffer buffer = archive.getEntry(member);
                if(buffer.capacity() > 0) {
                    WidgetDefinition definition = WidgetDefinition.decode(buffer, file << 16 | member);
                    definitions[file][member] = definition;
                    count++;
                }
            }
        }

        logger.info("Loaded " + count + " widget definitions.");
    }
    
    public static WidgetDefinition get(int hash) {
        return get(hash >> 16, hash & 0xffff);
    }

    public static WidgetDefinition get(int file, int member) {
        if(file < 0 || file >= definitions.length || member < 0 || member >= definitions[file].length) {
            return null;
        }
        return definitions[file][member];
    }
}
