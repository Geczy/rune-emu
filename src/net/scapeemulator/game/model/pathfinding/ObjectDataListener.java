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
import net.scapeemulator.game.model.definition.ObjectDefinitions;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.object.GroundObjectList.GroundObject;
import net.scapeemulator.game.model.object.GroundObjectListenerAdapter;

/**
 * Created by Hadyn Richard
 */
public final class ObjectDataListener extends GroundObjectListenerAdapter {

    private final TraversalMap traversalMap;

    public ObjectDataListener(TraversalMap traversalMap) {
        this.traversalMap = traversalMap;
    }

    @Override
    public void groundObjectUpdated(GroundObject object) {
        ObjectDefinition def = ObjectDefinitions.forId(object.getId());
        if(!def.isSolid()) {
            return;
        }
        
        Position position = object.getPosition();

        if(!traversalMap.regionInitialized(position.getX(), position.getY())) {
            traversalMap.initializeRegion(position.getX(), position.getY());
        }

        if(object.getType().isWall()) {
            traversalMap.markWall(object.getRotation(), position.getHeight(), position.getX(), position.getY(), object.getType(), def.isImpenetrable());
        }

        if(object.getType().getId() >= 9 && object.getType().getId() <= 12) {

            /* Flip the length and width if the object is rotated */
            int width = def.getWidth();
            int length = def.getLength();
            if(object.getRotation() == 1 || object.getRotation() == 3) {
                width = def.getLength();
                length = def.getWidth();
            }

            traversalMap.markOccupant(position.getHeight(), position.getX(), position.getY(), width, length);
        }
    }

    @Override
    public void groundObjectRotationUpdated(GroundObject object, int oldRotation) {
        ObjectDefinition def = ObjectDefinitions.forId(object.getId());
        if(!def.isSolid()) {
            return;
        }

        Position position = object.getPosition();

        if(!traversalMap.regionInitialized(position.getX(), position.getY())) {
            traversalMap.initializeRegion(position.getX(), position.getY());
        }

        if(object.getType().isWall()) {
            traversalMap.unmarkWall(oldRotation, position.getHeight(), position.getX(), position.getY(), object.getType(), def.isImpenetrable());
        }

        if(object.getType().getId() >= 9 && object.getType().getId() <= 12) {

            /* Flip the length and width if the object is rotated */
            int width = def.getWidth();
            int length = def.getLength();
            if(1 == oldRotation || oldRotation == 3) {
                width = def.getLength();
                length = def.getWidth();
            }

            //traversalMap.markOccupant(position.getHeight(), position.getX(), position.getY(), width, length);
        }
    }

    @Override
    public void groundObjectRemoved(GroundObject object) {
        ObjectDefinition def = ObjectDefinitions.forId(object.getId());
        if(!def.isSolid()) {
            return;
        }

        Position position = object.getPosition();

        if(!traversalMap.regionInitialized(position.getX(), position.getY())) {
            traversalMap.initializeRegion(position.getX(), position.getY());
        }

        if(object.getType().isWall()) {
            traversalMap.unmarkWall(object.getRotation(), position.getHeight(), position.getX(), position.getY(), object.getType(), def.isImpenetrable());
        }

        if(object.getType().getId() >= 9 && object.getType().getId() <= 12) {

            /* Flip the length and width if the object is rotated */
            int width = def.getWidth();
            int length = def.getLength();
            if(1 == object.getRotation() || object.getRotation() == 3) {
                width = def.getLength();
                length = def.getWidth();
            }

            //traversalMap.markOccupant(position.getHeight(), position.getX(), position.getY(), width, length);
        }
    }
}
