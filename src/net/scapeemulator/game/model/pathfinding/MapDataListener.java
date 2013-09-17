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

package net.scapeemulator.game.model.pathfinding;

import net.scapeemulator.cache.def.ObjectDefinition;
import net.scapeemulator.game.cache.MapListenerAdapter;
import net.scapeemulator.game.cache.MapLoader;
import net.scapeemulator.game.model.definition.ObjectDefinitions;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.object.ObjectType;

/**
 * Created by Hadyn Richard
 */
public final class MapDataListener extends MapListenerAdapter {

    /**
     * The traversal map to update when object or tile data is loaded.
     */
    private final TraversalMap traversalMap;

    /**
     * Constructs a new {@link MapDataListener};
     */
    public MapDataListener(TraversalMap traversalMap) {
        this.traversalMap = traversalMap;
    }

    @Override
    public void tileDecoded(int flags, Position position) {
        if((flags & MapLoader.BRIDGE_FLAG) != 0) {
            traversalMap.markBridge(position.getHeight(), position.getX(), position.getY());
        }

        if((flags & MapLoader.FLAG_CLIP) != 0) {
            traversalMap.markBlocked(position.getHeight(), position.getX(), position.getY());
        }
    }

    @Override
    public void objectDecoded(int id, int rotation, ObjectType type, Position position) {
        ObjectDefinition def = ObjectDefinitions.forId(id);
        if(!def.isSolid()) {
            return;
        }

        if(!traversalMap.regionInitialized(position.getX(), position.getY())) {
            traversalMap.initializeRegion(position.getX(), position.getY());
        }
        
        if(type.isWall()) {
            traversalMap.markWall(rotation, position.getHeight(), position.getX(), position.getY(), type, def.isImpenetrable());
        }

        if(type.getId() >= 9 && type.getId() <= 12) {

            /* Flip the length and width if the object is rotated */
            int width = def.getWidth();
            int length = def.getLength();
            if(1 == rotation || rotation == 3) {
                width = def.getLength();
                length = def.getWidth();
            }

            traversalMap.markOccupant(position.getHeight(), position.getX(), position.getY(), width, length);
        }
    }
}
