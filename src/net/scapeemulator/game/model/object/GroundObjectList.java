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

import net.scapeemulator.game.model.Entity;
import net.scapeemulator.game.model.Position;

import java.util.Map.Entry;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import net.scapeemulator.cache.def.ObjectDefinition;
import net.scapeemulator.game.model.definition.ObjectDefinitions;

/**
 * Created by Hadyn Richard
 */
public final class GroundObjectList {

    /**
     * The UID counter for all the ground objects that are created.
     */
    private static final AtomicInteger counter = new AtomicInteger(1);

    /**
     * The mapping for all the tiles in this list.
     */
    private final Map<Position, Tile> tiles = new LinkedHashMap<>();

    /**
     * The list of listeners registered to the list.
     */
    private final List<GroundObjectListener> listeners = new LinkedList<>();

    /**
     * The list of objects that have been updated.
     */
    private final Set<GroundObject> updatedObjects = new HashSet<>();

    /**
     * The flag for if objects that were updated should be listed.
     */
    private boolean recordUpdates = true;

    /**
     * The representation of a tile in the game world that contains ground objects.
     */
    private class Tile {

        /**
         * The mapping for the objects that exist on the tile.
         */
        private Map<ObjectGroup, GroundObject> objects = new LinkedHashMap<>();

        /**
         * Constructs a new {@link Tile};
         */
        public Tile() {}

        public boolean put(ObjectGroup group, GroundObject object) {
            /* Cannot contain multiple of the same group of object on a tile */
            if(objects.containsKey(group)) {
                return false;
            }

            /* Put the object into the mapping */
            object.setUid(counter.incrementAndGet());
            objects.put(group, object);

            /* Update the listeners */
            for(GroundObjectListener listener : listeners) {
                listener.groundObjectAdded(object);
            }

            if(recordUpdates) {

                /* Add the object to the updated objects set */
                updatedObjects.add(object);
            }
            return true;
        }

        public GroundObject get(int objectId) {
            for(Entry<ObjectGroup, GroundObject> entry : objects.entrySet()) {
                GroundObject object = entry.getValue();
                if(object.id == objectId) {
                    return object;
                }
            }
            return null;
        }

        public boolean contains(int objectId) {
            for(Entry<ObjectGroup, GroundObject> entry : objects.entrySet()) {
                GroundObject object = entry.getValue();
                if(object.id == objectId) {
                    return true;
                }
            }
            return false;
        }

        public void remove(ObjectGroup group) {
            GroundObject object = objects.remove(group);
            if(object != null) {

                /* Update the listeners */
                for(GroundObjectListener listener : listeners) {
                    listener.groundObjectRemoved(object);
                }

                if(recordUpdates) {

                    /* Remove the object from the updated objects set */
                    updatedObjects.remove(object);
                }
            }
        }
        
        public List<GroundObject> getObjects() {
            return new LinkedList<GroundObject>(objects.values());
        }
    }

    /**
     * The representation of a ground object registered to this list.
     */
    public class GroundObject extends Entity {

        /**
         * The data for the ground object.
         */
        private int id, rotation, uid;

        /**
         * The flag for if the object is hidden.
         */
        private boolean isHidden;

        /**
         * The type of object this ground object is defined as.
         */
        private final ObjectType type;

        /**
         * Constructs a new {@link GroundObject};
         */
        public GroundObject(Position position, int id, int rotation, ObjectType type) {
            this.position = position;
            this.id = id;
            this.rotation = rotation;
            this.type = type;
        }

        /**
         * Sets the object id of the ground object.
         */
        public void setId(int id) {
            this.id = id;

            if(!isHidden) {

                /* Update the listeners */
                for(GroundObjectListener listener : listeners) {
                    listener.groundObjectUpdated(this);
                }
            }

            if(recordUpdates) {

                /* Add to the updated objects list */
                updatedObjects.add(this);
            }
        }

        /**
         * Gets the object id of the ground object.
         */
        public int getId() {
            return id;
        }

        /**
         * Rotates an object by a certain amount.
         * @param amount The amount to rotate the object.
         */
        public void rotate(int amount) {
            setRotation(rotation + amount);
        }

        /**
         * Sets the rotation of the ground object.
         */
        public void setRotation(int rotation) {
            rotation &= 3;

            int oldRotation = this.rotation;
            this.rotation = rotation;


            if(!isHidden) {
                /* Update the listeners */
                for(GroundObjectListener listener : listeners) {
                    listener.groundObjectRotationUpdated(this, oldRotation);
                }

                for(GroundObjectListener listener : listeners) {
                    listener.groundObjectUpdated(this);
                }
            }

            if(recordUpdates) {

                /* Add to the updated objects list */
                updatedObjects.add(this);
            }
        }

        /**
         * Hides the object.
         */
        public void hide() {
            if(!isHidden) {

                /* Alert all the listeners */
                for(GroundObjectListener listener : listeners) {
                    listener.groundObjectRemoved(this);
                }

                if(recordUpdates) {

                    /* Add to the updated objects list */
                    updatedObjects.add(this);
                }

                isHidden = true;
            }
        }

        /**
         * Reveals the object if it is hidden.
         */
        public void reveal() {
           if(isHidden) {

               /* Alert all the listeners */
               for(GroundObjectListener listener : listeners) {
                   listener.groundObjectAdded(this);
               }

               if(recordUpdates) {

                   /* Add to the updated objects list */
                   updatedObjects.add(this);
               }

               isHidden = false;
           }
        }
        
        /**
         * Calculates the center Position of this object
         */
        public Position getCenterPosition() {
        	ObjectDefinition def = ObjectDefinitions.forId(id);
        	int centerX = position.getX() + (def.getWidth()/2);
        	int centerY = position.getY() + (def.getLength()/2);
        	return new Position(centerX, centerY);
        }
        
        /** 
         * Calculates the turn to position.
         */
        public Position getTurnToPosition(Position from) {
            ObjectDefinition def = ObjectDefinitions.forId(id);
            
            int width = def.getWidth();
            int length = def.getLength();
            if(rotation == 1 || rotation == 3) {
                width = def.getLength();
                length = def.getWidth();
            }
            
            int turnToX = from.getX(), turnToY = from.getY();          
            
            /* Within the width of the object */
            if(from.getX() >= position.getX() && from.getX() < position.getX() + width) {
               turnToY = position.getY();
            } 
            
            /* Within the length of the object */
            if(from.getY() >= position.getY() && from.getY() < position.getY() + width) {
               turnToX = position.getX();
            } 
            
            /* Upper left corner */
            if(from.getX() < position.getX() && from.getY() >= position.getY() + length) {
                turnToX = position.getX();
                turnToY = position.getY() + length - 1;
            }
            
            /* Upper right corner */
            if(from.getX() >= position.getX() + width && from.getY() >= position.getY() + length) {
                turnToX = position.getX() + width - 1;
                turnToY = position.getY() + length - 1;
            }         
                        
            /* Lower left corner */
            if(from.getX() < position.getX() + width && from.getY() < position.getY()) {
                turnToX = position.getX();
                turnToY = position.getY();
            }
            
            /* Lower right corner */
            if(from.getX() >= position.getX() + width && from.getY() < position.getY()) {
                turnToX = position.getX() + width - 1;
                turnToY = position.getY();
            }
            
            return new Position(turnToX, turnToY);
        }

        /**
         * Gets the rotation of the ground object.
         */
        public int getRotation() {
            return rotation;
        }

        /**
         * Sets the ground objects unique id.
         */
        public void setUid(int uid) {
            this.uid = uid;
        }

        /**
         * Gets the ground objects unique id.
         */
        public int getUid() {
            return uid;
        }

        /**
         * Gets if the object is hidden.
         * @return If the object is hidden.
         */
        public boolean isHidden() {
            return isHidden;
        }

        /**
         * Gets the object type.
         */
        public ObjectType getType() {
            return type;
        }
    }

    /**
     * Adds a listener to the list.
     * @param listener The listener to add.
     */
    public void addListener(GroundObjectListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the list.
     * @param listener The listener to remove.
     */
    public void removeListener(GroundObjectListener listener) {
        listeners.remove(listener);
    }

    /**
     * Sets if the list will record update objects.
     * @param recordUpdates The flag.
     */
    public void setRecordUpdates(boolean recordUpdates) {
        this.recordUpdates = recordUpdates;
    }

    /**
     * Fires a ground item added message for each ground object.
     */
    public void fireAllEvents(GroundObjectListener listener) {
        for(Entry<Position, Tile> entry : tiles.entrySet()) {
            for(GroundObject object : entry.getValue().getObjects()) {
                if(!object.isHidden) {
                    listener.groundObjectAdded(object);
                } else {
                    listener.groundObjectRemoved(object);
                }
            }
        }
    }

    /**
     * Fires a ground item added event for each updated ground object.
     */
    public void fireEvents(GroundObjectListener listener) {
        for(GroundObject object : updatedObjects) {
            if(!object.isHidden) {
                listener.groundObjectAdded(object);
            } else {
                listener.groundObjectRemoved(object);
            }
        }
    }

    public GroundObject put(Position position, int objectId, int rotation, ObjectType type) {
        GroundObject object = new GroundObject(position, objectId, rotation, type);
        Tile tile = tiles.get(position);
        if(tile == null) {
            tile = new Tile();
            tiles.put(position, tile);
        }
        return tile.put(object.getType().getObjectGroup(), object) ? object : null;
    }
    
    public GroundObject get(int objectId, Position position) {
        Tile tile = tiles.get(position);
        if(tile == null) {
            return null;
        }
        return tile.get(objectId);
    }
    
    public boolean contains(int objectId, Position position) {
        Tile tile = tiles.get(position);
        if(tile == null) {
            return false;
        }
        return tile.contains(objectId);
    }

    public void remove(Position position, ObjectGroup group) {
        Tile tile = tiles.get(position);
        if(tile == null) {
            return;
        }
        tile.remove(group);
    }
}
