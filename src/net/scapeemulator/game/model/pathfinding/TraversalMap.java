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

import static net.scapeemulator.game.model.object.ObjectOrientation.EAST;
import static net.scapeemulator.game.model.object.ObjectOrientation.NORTH;
import static net.scapeemulator.game.model.object.ObjectOrientation.SOUTH;
import static net.scapeemulator.game.model.object.ObjectOrientation.WEST;
import static net.scapeemulator.game.model.pathfinding.Tile.BLOCKED;
import static net.scapeemulator.game.model.pathfinding.Tile.BRIDGE;
import static net.scapeemulator.game.model.pathfinding.Tile.IMPENETRABLE_OCCUPANT;
import static net.scapeemulator.game.model.pathfinding.Tile.IMPENETRABLE_WALL_EAST;
import static net.scapeemulator.game.model.pathfinding.Tile.IMPENETRABLE_WALL_NORTH;
import static net.scapeemulator.game.model.pathfinding.Tile.IMPENETRABLE_WALL_NORTH_EAST;
import static net.scapeemulator.game.model.pathfinding.Tile.IMPENETRABLE_WALL_NORTH_WEST;
import static net.scapeemulator.game.model.pathfinding.Tile.IMPENETRABLE_WALL_SOUTH;
import static net.scapeemulator.game.model.pathfinding.Tile.IMPENETRABLE_WALL_SOUTH_EAST;
import static net.scapeemulator.game.model.pathfinding.Tile.IMPENETRABLE_WALL_SOUTH_WEST;
import static net.scapeemulator.game.model.pathfinding.Tile.IMPENETRABLE_WALL_WEST;
import static net.scapeemulator.game.model.pathfinding.Tile.OCCUPANT;
import static net.scapeemulator.game.model.pathfinding.Tile.WALL_EAST;
import static net.scapeemulator.game.model.pathfinding.Tile.WALL_NORTH;
import static net.scapeemulator.game.model.pathfinding.Tile.WALL_NORTH_EAST;
import static net.scapeemulator.game.model.pathfinding.Tile.WALL_NORTH_WEST;
import static net.scapeemulator.game.model.pathfinding.Tile.WALL_SOUTH;
import static net.scapeemulator.game.model.pathfinding.Tile.WALL_SOUTH_EAST;
import static net.scapeemulator.game.model.pathfinding.Tile.WALL_SOUTH_WEST;
import static net.scapeemulator.game.model.pathfinding.Tile.WALL_WEST;
import net.scapeemulator.game.model.object.ObjectType;

/**
 * Created by Hadyn Richard
 */
public final class TraversalMap {

    /**
     * The size of one side of the region array.
     */
    public static final int SIZE = 256;

    /**
     * The size of a region.
     */
    public static final int REGION_SIZE = 64;

    /**
     * The maximum plane.
     */
    public static final int MAXIMUM_PLANE = 4;

    /**
     * The regions for the traversal data.
     */
    private final Region[] regions = new Region[SIZE * SIZE];


    /**
     * Created by Hadyn Richard
     */
    private class Region {

        /**
         * The flags within the region.
         */
        private Tile[][] tiles;

        /**
         * Constructs a new {@link Region};
         */
        public Region() {
            tiles = new Tile[MAXIMUM_PLANE][REGION_SIZE * REGION_SIZE];
            for(int i = 0; i < MAXIMUM_PLANE; i++) {
                for(int j = 0; j < REGION_SIZE * REGION_SIZE; j++) {
                    tiles[i][j] = new Tile();
                }
            }
        }

        public Tile getTile(int plane, int x, int y) {
            return tiles[plane][x + y * REGION_SIZE];
        }
    }

    /**
     * Constructs a new {@link TraversalMap};
     */
    public TraversalMap() {}

    /**
     * Initializes the region at the specified coordinates.
     */
    public void initializeRegion(int x, int y) {
        
        /* Calculate the coordinates */
        int regionX = x >> 6, regionY = y >> 6; 
        
        regions[regionX + regionY * SIZE] = new Region();
    }

    /**
     * Gets if the set contains a region for the specified coordinates.
     */
    public boolean regionInitialized(int x, int y) {

        /* Calculate the coordinates */
        int regionX = x >> 6, regionY = y >> 6;

        /* Get if the region is not null */
        return regions[regionX + regionY * SIZE] != null;
    }

    public void markWall(int rotation, int plane, int x, int y, ObjectType type, boolean impenetrable) {
        switch(type) {
            case TYPE_0:
                if (rotation == WEST) {
                    set(plane, x, y, WALL_WEST);
                    set(plane, x - 1, y, WALL_EAST);
                }
                if (rotation == NORTH) {
                    set(plane, x, y, WALL_NORTH);
                    set(plane, x, y + 1, WALL_SOUTH);
                }
                if (rotation == EAST) {
                    set(plane, x, y, WALL_EAST);
                    set(plane, x + 1, y, WALL_WEST);
                }
                if (rotation == SOUTH) {
                    set(plane, x, y, WALL_SOUTH);
                    set(plane, x, y - 1, WALL_NORTH);
                }
                break;

            case TYPE_2:
                if (rotation == WEST) {
                    set(plane, x, y, WALL_WEST | WALL_NORTH);
                    set(plane, x - 1, y, WALL_EAST);
                    set(plane, x, y + 1, WALL_SOUTH);
                }
                if (rotation == NORTH) {
                    set(plane, x, y, WALL_EAST | WALL_NORTH);
                    set(plane, x, y + 1, WALL_SOUTH);
                    set(plane, x + 1, y, WALL_WEST);
                }
                if (rotation == EAST) {
                    set(plane, x, y, WALL_EAST | WALL_SOUTH);
                    set(plane, x + 1, y, WALL_WEST);
                    set(plane, x, y - 1, WALL_NORTH);
                }
                if (rotation == SOUTH) {
                    set(plane, x, y, WALL_EAST | WALL_SOUTH);
                    set(plane, x, y - 1, WALL_WEST);
                    set(plane, x - 1, y, WALL_NORTH);
                }
                break;
            
            case TYPE_1:
            case TYPE_3:
                if (rotation == WEST) {
                    set(plane, x, y, WALL_NORTH_WEST);
                    set(plane, x - 1, y + 1, WALL_SOUTH_EAST);
                }
                if (rotation == NORTH) {
                    set(plane, x, y, WALL_NORTH_EAST);
                    set(plane, x + 1, y + 1, WALL_SOUTH_WEST);
                }
                if (rotation == EAST) {
                    set(plane, x, y, WALL_SOUTH_EAST);
                    set(plane, x + 1, y - 1, WALL_NORTH_WEST);
                }
                if (rotation == SOUTH) {
                    set(plane, x, y, WALL_SOUTH_WEST);
                    set(plane, x - 1, y - 1, WALL_SOUTH_WEST);
                }
                break;
        }
    }

    public void unmarkWall(int rotation, int plane, int x, int y, ObjectType type, boolean impenetrable) {
        switch(type) {
            case TYPE_0:
                if (rotation == WEST) {
                    unset(plane, x, y, WALL_WEST);
                    unset(plane, x - 1, y, WALL_EAST);
                }
                if (rotation == NORTH) {
                    unset(plane, x, y, WALL_NORTH);
                    unset(plane, x, y + 1, WALL_SOUTH);
                }
                if (rotation == EAST) {
                    unset(plane, x, y, WALL_EAST);
                    unset(plane, x + 1, y, WALL_WEST);
                }
                if (rotation == SOUTH) {
                    unset(plane, x, y, WALL_SOUTH);
                    unset(plane, x, y - 1, WALL_NORTH);
                }
                break;

            case TYPE_2:
                if (rotation == WEST) {
                    unset(plane, x, y, WALL_WEST | WALL_NORTH);
                    unset(plane, x - 1, y, WALL_EAST);
                    unset(plane, x, y + 1, WALL_SOUTH);
                }
                if (rotation == NORTH) {
                    unset(plane, x, y, WALL_EAST | WALL_NORTH);
                    unset(plane, x, y + 1, WALL_SOUTH);
                    unset(plane, x + 1, y, WALL_WEST);
                }
                if (rotation == EAST) {
                    unset(plane, x, y, WALL_EAST | WALL_SOUTH);
                    unset(plane, x + 1, y, WALL_WEST);
                    unset(plane, x, y - 1, WALL_NORTH);
                }
                if (rotation == SOUTH) {
                    unset(plane, x, y, WALL_EAST | WALL_SOUTH);
                    unset(plane, x, y - 1, WALL_WEST);
                    unset(plane, x - 1, y, WALL_NORTH);
                }
                break;

            case TYPE_1:
            case TYPE_3:
                if (rotation == WEST) {
                    unset(plane, x, y, WALL_NORTH_WEST);
                    unset(plane, x - 1, y + 1, WALL_SOUTH_EAST);
                }
                if (rotation == NORTH) {
                    unset(plane, x, y, WALL_NORTH_EAST);
                    unset(plane, x + 1, y + 1, WALL_SOUTH_WEST);
                }
                if (rotation == EAST) {
                    unset(plane, x, y, WALL_SOUTH_EAST);
                    unset(plane, x + 1, y - 1, WALL_NORTH_WEST);
                }
                if (rotation == SOUTH) {
                    unset(plane, x, y, WALL_SOUTH_WEST);
                    unset(plane, x - 1, y - 1, WALL_SOUTH_WEST);
                }
                break;
        }
    }
    
    public void markBlocked(int plane, int x, int y) {

        /* Calculate the coordinates */
        int regionX = x >> 6, regionY = y >> 6;

        /* Calculate the local coordinates */
        int localX = x & 0x3f, localY = y & 0x3f;

        Region region = regions[regionX + regionY * SIZE];
        if(region == null) {
            return;
        }

        int modifiedPlane = plane;
        if((region.getTile(1, localX, localY).flags() & BRIDGE) != 0) {
            modifiedPlane = plane - 1;
        }

        region.getTile(modifiedPlane, x & 0x3f, y & 0x3f).set(BLOCKED);
    }

    public void markOccupant(int plane, int x, int y, int width, int length) {
        for(int offsetX = 0; offsetX < width; offsetX++) {
            for(int offsetY = 0; offsetY < length; offsetY++) {
                set(plane, x + offsetX, y + offsetY, OCCUPANT);
            }
        }
    }

    public void markBridge(int plane, int x, int y) {
        set(plane, x, y, BRIDGE);
    }
    
    public boolean isTraversableNorth(int plane, int x, int y, int size) {
        for(int offsetX = 0; offsetX < size; offsetX++) {
            for(int offsetY = 0; offsetY < size; offsetY++) {
                if(!isTraversableNorth(plane, x + offsetX, y + offsetY)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isTraversableNorth(int plane, int x, int y) {
        return isTraversableNorth(plane, x, y, false);
    }

    /**
     * Tests if from the specified position it can be traversed north.
     */
    public boolean isTraversableNorth(int plane, int x, int y, boolean impenetrable) {
        if(impenetrable) {
            return isInactive(plane, x, y + 1, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_SOUTH);
        }
        return isInactive(plane, x, y + 1, WALL_SOUTH | OCCUPANT | BLOCKED);
    }

    public boolean isTraversableSouth(int plane, int x, int y, int size) {
        for(int offsetX = 0; offsetX < size; offsetX++) {
            for(int offsetY = 0; offsetY < size; offsetY++) {
                if(!isTraversableSouth(plane, x + offsetX, y + offsetY)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isTraversableSouth(int plane, int x, int y) {
        return isTraversableSouth(plane, x, y, false);
    }

    /**
     * Tests if from the specified position it can be traversed south.
     */
    public boolean isTraversableSouth(int plane, int x, int y, boolean impenetrable) {
        if(impenetrable) {
            return isInactive(plane, x, y - 1, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_NORTH);
        }
        return isInactive(plane, x, y - 1, WALL_NORTH | OCCUPANT | BLOCKED);
    }

    public boolean isTraversableEast(int plane, int x, int y, int size) {
        for(int offsetX = 0; offsetX < size; offsetX++) {
            for(int offsetY = 0; offsetY < size; offsetY++) {
                if(!isTraversableEast(plane, x + offsetX, y + offsetY)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isTraversableEast(int plane, int x, int y) {
        return isTraversableEast(plane, x, y, false);
    }

    /**
     * Tests if from the specified position it can be traversed south.
     */
    public boolean isTraversableEast(int plane, int x, int y, boolean impenetrable) {
        if(impenetrable) {
            return isInactive(plane, x + 1, y, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_WEST);
        }
        return isInactive(plane, x + 1, y, WALL_WEST | OCCUPANT | BLOCKED);
    }

    public boolean isTraversableWest(int plane, int x, int y, int size) {
        for(int offsetX = 0; offsetX < size; offsetX++) {
            for(int offsetY = 0; offsetY < size; offsetY++) {
                if(!isTraversableWest(plane, x + offsetX, y + offsetY)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isTraversableWest(int plane, int x, int y) {
        return isTraversableWest(plane, x, y, false);
    }

    /**
     * Tests if from the specified position it can be traversed south.
     */
    public boolean isTraversableWest(int plane, int x, int y, boolean impenetrable) {
        if(impenetrable) {
            return isInactive(plane, x - 1, y, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_EAST);
        }
        return isInactive(plane, x - 1, y, WALL_EAST | OCCUPANT | BLOCKED);
    }

    public boolean isTraversableNorthEast(int plane, int x, int y, int size) {
        for(int offsetX = 0; offsetX < size; offsetX++) {
            for(int offsetY = 0; offsetY < size; offsetY++) {
                if(!isTraversableNorthEast(plane, x + offsetX, y + offsetY)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isTraversableNorthEast(int plane, int x, int y) {
        return isTraversableNorthEast(plane, x, y, false);
    }

    /**
     * Tests if from the specified position it can be traversed south.
     */
    public boolean isTraversableNorthEast(int plane, int x, int y, boolean impenetrable) {
        if(impenetrable) {
            return isInactive(plane, x + 1, y + 1, IMPENETRABLE_WALL_WEST | IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_WALL_SOUTH_WEST | OCCUPANT)
                && isInactive(plane, x + 1, y, IMPENETRABLE_WALL_WEST | IMPENETRABLE_OCCUPANT)
                && isInactive(plane, x, y + 1, IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_OCCUPANT) ;
        }
        return isInactive(plane, x + 1, y + 1, WALL_WEST | WALL_SOUTH | WALL_SOUTH_WEST | OCCUPANT  | BLOCKED)
            && isInactive(plane, x + 1, y, WALL_WEST | OCCUPANT | BLOCKED)
            && isInactive(plane, x, y + 1, WALL_SOUTH | OCCUPANT | BLOCKED) ;
    }

    public boolean isTraversableNorthWest(int plane, int x, int y, int size) {
        for(int offsetX = 0; offsetX < size; offsetX++) {
            for(int offsetY = 0; offsetY < size; offsetY++) {
                if(!isTraversableNorthWest(plane, x + offsetX, y + offsetY)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isTraversableNorthWest(int plane, int x, int y) {
        return isTraversableNorthWest(plane, x, y, false);
    }

    /**
     * Tests if from the specified position it can be traversed south.
     */
    public boolean isTraversableNorthWest(int plane, int x, int y, boolean impenetrable) {
        if(impenetrable) {
            return isInactive(plane, x - 1, y + 1, IMPENETRABLE_WALL_EAST | IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_WALL_SOUTH_EAST | OCCUPANT)
                    && isInactive(plane, x - 1, y, IMPENETRABLE_WALL_EAST | IMPENETRABLE_OCCUPANT)
                    && isInactive(plane, x, y + 1, IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_OCCUPANT) ;
        }
        return isInactive(plane, x - 1, y + 1, WALL_EAST | WALL_SOUTH | WALL_SOUTH_EAST | OCCUPANT  | BLOCKED)
                && isInactive(plane, x - 1, y, WALL_EAST | OCCUPANT | BLOCKED)
                && isInactive(plane, x, y + 1, WALL_SOUTH | OCCUPANT | BLOCKED) ;
    }

    public boolean isTraversableSouthEast(int plane, int x, int y, int size) {
        for(int offsetX = 0; offsetX < size; offsetX++) {
            for(int offsetY = 0; offsetY < size; offsetY++) {
                if(!isTraversableSouthEast(plane, x + offsetX, y + offsetY)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isTraversableSouthEast(int plane, int x, int y) {
        return isTraversableSouthEast(plane, x, y, false);
    }

    /**
     * Tests if from the specified position it can be traversed south.
     */
    public boolean isTraversableSouthEast(int plane, int x, int y, boolean impenetrable) {
        if(impenetrable) {
            return isInactive(plane, x + 1, y - 1, IMPENETRABLE_WALL_WEST | IMPENETRABLE_WALL_NORTH | IMPENETRABLE_WALL_NORTH_WEST | OCCUPANT)
                    && isInactive(plane, x + 1, y, IMPENETRABLE_WALL_WEST | IMPENETRABLE_OCCUPANT)
                    && isInactive(plane, x, y - 1, IMPENETRABLE_WALL_NORTH | IMPENETRABLE_OCCUPANT) ;
        }
        return isInactive(plane, x + 1, y - 1, WALL_WEST | WALL_NORTH | WALL_NORTH_WEST | OCCUPANT  | BLOCKED)
                && isInactive(plane, x + 1, y, WALL_WEST | OCCUPANT | BLOCKED)
                && isInactive(plane, x, y - 1, WALL_NORTH | OCCUPANT | BLOCKED) ;
    }

    public boolean isTraversableSouthWest(int plane, int x, int y, int size) {
        for(int offsetX = 0; offsetX < size; offsetX++) {
            for(int offsetY = 0; offsetY < size; offsetY++) {
                if(!isTraversableSouthWest(plane, x + offsetX, y + offsetY)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isTraversableSouthWest(int plane, int x, int y) {
        return isTraversableSouthWest(plane, x, y, false);
    }

    /**
     * Tests if from the specified position it can be traversed south.
     */
    public boolean isTraversableSouthWest(int plane, int x, int y, boolean impenetrable) {
        if(impenetrable) {
            return isInactive(plane, x - 1, y - 1, IMPENETRABLE_WALL_EAST | IMPENETRABLE_WALL_NORTH | IMPENETRABLE_WALL_NORTH_EAST | OCCUPANT)
                    && isInactive(plane, x - 1, y, IMPENETRABLE_WALL_EAST | IMPENETRABLE_OCCUPANT)
                    && isInactive(plane, x, y - 1, IMPENETRABLE_WALL_NORTH | IMPENETRABLE_OCCUPANT) ;
        }
        return isInactive(plane, x - 1, y - 1, WALL_EAST | WALL_NORTH | WALL_NORTH_EAST | OCCUPANT  | BLOCKED)
                && isInactive(plane, x - 1, y, WALL_EAST | OCCUPANT | BLOCKED)
                && isInactive(plane, x, y - 1, WALL_NORTH | OCCUPANT | BLOCKED) ;
    }
    
    public void set(int plane, int x, int y, int flag) {
        
        /* Calculate the coordinates */
        int regionX = x >> 6, regionY = y >> 6;
        
        Region region = regions[regionX + regionY * SIZE];
        if(region == null) {
            return;
        }

        region.getTile(plane, x & 0x3f, y & 0x3f).set(flag);
    }

    public boolean isInactive(int plane, int x, int y, int flag) {

        /* Calculate the region coordinates */
        int regionX = x >> 6, regionY = y >> 6;

        /* Calculate the local region coordinates */
        int localX = x & 0x3f, localY = y & 0x3f;

        Region region = regions[regionX + regionY * SIZE];
        if(region == null) {
            return false;
        }

        int modifiedPlane = plane;
        if(region.getTile(1, localX, localY).isActive(BRIDGE)) {
            modifiedPlane = plane + 1;
        }

        return region.getTile(modifiedPlane, localX, localY).isInactive(flag);
    }

    public void unset(int plane, int x, int y, int flag) {

        /* Calculate the coordinates */
        int regionX = x >> 6, regionY = y >> 6;

        Region region = regions[regionX + regionY * SIZE];
        if(region == null) {
            return;
        }

        region.getTile(plane, x & 0x3f, y & 0x3f).unset(flag);
    }
}
