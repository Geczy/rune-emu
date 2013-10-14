package net.scapeemulator.game.model.mob;

import java.util.LinkedList;
import java.util.List;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.World;
import net.scapeemulator.game.model.pathfinding.TraversalMap;

public enum Direction {

    NONE(-1), NORTH(1), NORTH_EAST(2), EAST(4), SOUTH_EAST(7), SOUTH(6), SOUTH_WEST(5), WEST(3), NORTH_WEST(0);
    
    private final int intValue;

    private Direction(int intValue) {
        this.intValue = intValue;
    }
    
    public Direction getInverted() {
        switch(this) {
            case NONE:
                return NONE;
            case NORTH:
                return SOUTH;
            case NORTH_EAST:
                return SOUTH_WEST;
            case EAST:
                return WEST;
            case SOUTH_EAST:
                return NORTH_WEST;
            case SOUTH:
                return NORTH;
            case SOUTH_WEST:
                return NORTH_EAST;
            case WEST:
                return EAST;
            case NORTH_WEST:
                return SOUTH_EAST;
        }
        throw new RuntimeException();
    }

    public static List<Position> getNearbyTraversableTiles(Position from, int size) {
        TraversalMap traversalMap = World.getWorld().getTraversalMap();
        List<Position> positions = new LinkedList<>();
        for (Direction direction : values()) {
            switch (direction) {
                case NORTH:
                    if (traversalMap.isTraversableNorth(from.getHeight(), from.getX(), from.getY(), size)) {
                        positions.add(new Position(from.getX(), from.getY() + 1, from.getHeight()));
                    }
                    break;
                case SOUTH:
                    if (traversalMap.isTraversableSouth(from.getHeight(), from.getX(), from.getY(), size)) {
                        positions.add(new Position(from.getX(), from.getY() - 1, from.getHeight()));
                    }
                    break;
                case EAST:
                    if (traversalMap.isTraversableEast(from.getHeight(), from.getX(), from.getY(), size)) {
                        positions.add(new Position(from.getX() + 1, from.getY(), from.getHeight()));
                    }
                    break;
                case WEST:
                    if (traversalMap.isTraversableWest(from.getHeight(), from.getX(), from.getY(), size)) {
                        positions.add(new Position(from.getX() - 1, from.getY(), from.getHeight()));
                    }
                    break;
                case NORTH_EAST:
                    if (traversalMap.isTraversableNorthEast(from.getHeight(), from.getX(), from.getY(), size)) {
                        positions.add(new Position(from.getX() + 1, from.getY() + 1, from.getHeight()));
                    }
                    break;
                case NORTH_WEST:
                    if (traversalMap.isTraversableNorthWest(from.getHeight(), from.getX(), from.getY(), size)) {
                        positions.add(new Position(from.getX() - 1, from.getY() + 1, from.getHeight()));
                    }
                    break;
                case SOUTH_EAST:
                    if (traversalMap.isTraversableSouthEast(from.getHeight(), from.getX(), from.getY(), size)) {
                        positions.add(new Position(from.getX() + 1, from.getY() - 1, from.getHeight()));
                    }
                    break;
                case SOUTH_WEST:
                    if (traversalMap.isTraversableSouthWest(from.getHeight(), from.getX(), from.getY(), size)) {
                        positions.add(new Position(from.getX() - 1, from.getY() - 1, from.getHeight()));
                    }
                    break;
            }
        }
        return positions;
    }

    public static boolean isTraversable(Position from, Direction direction, int size) {
        TraversalMap traversalMap = World.getWorld().getTraversalMap();
        switch (direction) {
            case NORTH:
                return traversalMap.isTraversableNorth(from.getHeight(), from.getX(), from.getY(), size);
            case SOUTH:
                return traversalMap.isTraversableSouth(from.getHeight(), from.getX(), from.getY(), size);
            case EAST:
                return traversalMap.isTraversableEast(from.getHeight(), from.getX(), from.getY(), size);
            case WEST:
                return traversalMap.isTraversableWest(from.getHeight(), from.getX(), from.getY(), size);
            case NORTH_EAST:
                return traversalMap.isTraversableNorthEast(from.getHeight(), from.getX(), from.getY(), size);
            case NORTH_WEST:
                return traversalMap.isTraversableNorthWest(from.getHeight(), from.getX(), from.getY(), size);
            case SOUTH_EAST:
                return traversalMap.isTraversableSouthEast(from.getHeight(), from.getX(), from.getY(), size);
            case SOUTH_WEST:
                return traversalMap.isTraversableSouthWest(from.getHeight(), from.getX(), from.getY(), size);
            case NONE:
                return true;
            default:
                throw new RuntimeException("unknown type");
        }
    }

    public int toInteger() {
        return intValue;
    }

    public static Direction between(Position cur, Position next) {
        int deltaX = next.getX() - cur.getX();
        int deltaY = next.getY() - cur.getY();

        if (deltaY == 1) {
            if (deltaX == 1) {
                return NORTH_EAST;
            } else if (deltaX == 0) {
                return NORTH;
            } else if (deltaX == -1) {
                return NORTH_WEST;
            }
        } else if (deltaY == -1) {
            if (deltaX == 1) {
                return SOUTH_EAST;
            } else if (deltaX == 0) {
                return SOUTH;
            } else if (deltaX == -1) {
                return SOUTH_WEST;
            }
        } else if (deltaY == 0) {
            if (deltaX == 1) {
                return EAST;
            } else if (deltaX == 0) {
                return NONE;
            } else if (deltaX == -1) {
                return WEST;
            }
        }
        throw new IllegalArgumentException(deltaX + " " + deltaY);
    }
}
