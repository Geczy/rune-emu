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

package net.scapeemulator.game.model.object;

import net.scapeemulator.game.cache.MapListenerAdapter;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.definition.ObjectDefinitions;

/**
 * Created by Hadyn Richard
 */
public final class GroundObjectPopulator extends MapListenerAdapter {

    /**
     * The ground object list to populate.
     */
    private final GroundObjectList list;

    /**
     * Constructs a new {@link GroundObjectPopulator};
     */
    public GroundObjectPopulator(GroundObjectList list) {
        this.list = list;
    }

    @Override
    public void objectDecoded(int id, int rotation, ObjectType type, Position position) {

        /* Stop the list from appending the object to the updated list */
        list.setRecordUpdates(false);

        /* Only insert an object if it has a name */
        if(!"null".equals(ObjectDefinitions.forId(id).getName())) {
            list.put(position, id, rotation, type);
        }

        /* Reset the record updates state of the list */
        list.setRecordUpdates(true);
    }
}
