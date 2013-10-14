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

import java.io.IOException;

import net.scapeemulator.cache.Archive;
import net.scapeemulator.cache.Cache;
import net.scapeemulator.cache.Container;
import net.scapeemulator.cache.ReferenceTable;
import net.scapeemulator.cache.def.VarbitDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Hadyn Richard
 */
public class VarbitDefinitions {

    private static final Logger logger = LoggerFactory.getLogger(ObjectDefinitions.class);
    private static VarbitDefinition[] definitions;

    public static void init(Cache cache) throws IOException {
        int count = 0;

        Container tableContainer = Container.decode(cache.getStore().read(255, 22));
        ReferenceTable table = ReferenceTable.decode(tableContainer.getData());

        int files = table.capacity();
        definitions = new VarbitDefinition[files * 1024];

        for (int file = 0; file < files; file++) {
            ReferenceTable.Entry entry = table.getEntry(file);
            if (entry == null)
                continue;

            Archive archive = Archive.decode(cache.read(22, file).getData(), entry.size());
            int nonSparseMember = 0;
            for (int member = 0; member < entry.capacity(); member++) {
                ReferenceTable.ChildEntry childEntry = entry.getEntry(member);
                if (childEntry == null)
                    continue;


                int id = file * 1024 + member;
                VarbitDefinition definition = VarbitDefinition.decode(archive.getEntry(nonSparseMember++));
                definitions[id] = definition;
                count++;
            }
        }

        logger.info("Loaded " + count + " varbit definitions.");
    }

    public static int count() {
        return definitions.length;
    }

    public static VarbitDefinition forId(int id) {
        return definitions[id];
    }

}
