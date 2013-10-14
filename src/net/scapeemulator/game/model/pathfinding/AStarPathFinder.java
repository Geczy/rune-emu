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

import java.util.HashSet;
import java.util.Set;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.World;

/**
 * @author Graham Edgecombe
 */
public final class AStarPathFinder extends PathFinder {

    /**
     * The cost of moving in a straight line.
     */
    private static final int COST_STRAIGHT = 10;

    /**
     * Represents a node used by the A* algorithm.
     * @author Graham Edgecombe
     */
    private static class Node implements Comparable<Node> {

        /**
         * The parent node.
         */
        private Node parent;

        /**
         * The cost.
         */
        private int cost;

        /**
         * The heuristic.
         */
        private int heuristic;

        /**
         * The depth.
         */
        private int depth;

        /**
         * The x coordinate.
         */
        private final int x;

        /**
         * The y coordinate.
         */
        private final int y;

        /**
         * Creates a node.
         * @param x The x coordinate.
         * @param y The y coordinate.
         */
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Sets the parent.
         * @param parent The parent.
         */
        public void setParent(Node parent) {
            this.parent = parent;
        }

        /**
         * Gets the parent node.
         * @return The parent node.
         */
        public Node getParent() {
            return parent;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }

        /**
         * Gets the X coordinate.
         * @return The X coordinate.
         */
        public int getX() {
            return x;
        }

        /**
         * Gets the Y coordinate.
         * @return The Y coordinate.
         */
        public int getY() {
            return y;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + cost;
            result = prime * result + depth;
            result = prime * result + heuristic;
            result = prime * result
                    + ((parent == null) ? 0 : parent.hashCode());
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            if (cost != other.cost)
                return false;
            if (depth != other.depth)
                return false;
            if (heuristic != other.heuristic)
                return false;
            if (parent == null) {
                if (other.parent != null)
                    return false;
            } else if (!parent.equals(other.parent))
                return false;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        @Override
        public int compareTo(Node arg0) {
            return cost - arg0.cost;
        }

    }

    private Node current;
    private Node[][] nodes;
    private Set<Node> closed = new HashSet<>();
    private Set<Node> open = new HashSet<>();

    @Override
    public Path find(Position position, int width, int length, int srcX, int srcY, int dstX, int dstY, int size) {
        if(dstX < 0 || dstY < 0 || dstX >= width || dstY >= length) {
            return null; // out of range
        }

        TraversalMap map = World.getWorld().getTraversalMap();
        
        nodes = new Node[width][length];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < length; y++) {
                nodes[x][y] = new Node(x, y);
            }
        }

        open.add(nodes[srcX][srcY]);

        while(open.size() > 0) {
            current = getLowestCost();
            if(current == nodes[dstX][dstY]) {
                break;
            }
            open.remove(current);
            closed.add(current);

            int x = current.getX(), y = current.getY();

            // south
            if(y > 0 && map.isTraversableSouth(position.getHeight(), position.getX() + x, position.getY() + y, size)) {
                Node n = nodes[x][y - 1];
                examineNode(n);
            }
            // west
            if(x > 0 && map.isTraversableWest(position.getHeight(), position.getX() + x, position.getY() + y, size)) {
                Node n = nodes[x - 1][y];
                examineNode(n);
            }
            // north
            if(y < length - 1 && map.isTraversableNorth(position.getHeight(), position.getX() + x, position.getY() + y, size)) {
                Node n = nodes[x][y + 1];
                examineNode(n);
            }
            // east
            if(x < width - 1 && map.isTraversableEast(position.getHeight(), position.getX() + x, position.getY() + y, size)) {
                Node n = nodes[x + 1][y];
                examineNode(n);
            }
            // south west
            if(x > 0 && y > 0 && map.isTraversableSouthWest(position.getHeight(), position.getX() + x, position.getY() + y, size)) {
                Node n = nodes[x - 1][y - 1];
                examineNode(n);
            }
            // north west
            if(x > 0 && y < length - 1 && map.isTraversableNorthWest(position.getHeight(), position.getX() + x, position.getY() + y, size)) {
                Node n = nodes[x - 1][y + 1];
                examineNode(n);
            }

            // south east
            if(x < width - 1 && y > 0 && map.isTraversableSouthEast(position.getHeight(), position.getX() + x, position.getY() + y, size)) {
                Node n = nodes[x + 1][y - 1];
                examineNode(n);
            }
            // north east
            if(x < width - 1 && y < length - 1 && map.isTraversableNorthEast(position.getHeight(), position.getX() + x, position.getY() + y, size)) {
                Node n = nodes[x + 1][y + 1];
                examineNode(n);
            }


        }

        if(nodes[dstX][dstY].getParent() == null) {
            return null;
        }

        Path p = new Path();
        Node n = nodes[dstX][dstY];
        while(n != nodes[srcX][srcY]) {
            p.addFirst(new Position(n.getX() + position.getX(), n.getY() + position.getY()));
            n = n.getParent();
        }

        return p;
    }

    private Node getLowestCost() {
        Node curLowest = null;
        for(Node n : open) {
            if(curLowest == null) {
                curLowest = n;
            } else {
                if(n.getCost() < curLowest.getCost()) {
                    curLowest = n;
                }
            }
        }
        return curLowest;
    }

    private void examineNode(Node n) {
        int heuristic = estimateDistance(current, n);
        int nextStepCost = current.getCost() + heuristic;
        if(nextStepCost < n.getCost()) {
            open.remove(n);
            closed.remove(n);
        }
        if(!open.contains(n) && !closed.contains(n)) {
            n.setParent(current);
            n.setCost(nextStepCost);
            open.add(n);
        }
    }

    /**
     * Estimates a distance between the two points.
     * @param src The source node.
     * @param dst The distance node.
     * @return The distance.
     */
    public int estimateDistance(Node src, Node dst) {
        int deltaX = src.getX() - dst.getX();
        int deltaY = src.getY() - dst.getY();
        return (Math.abs(deltaX) + Math.abs(deltaY)) *  COST_STRAIGHT;
    }

}
