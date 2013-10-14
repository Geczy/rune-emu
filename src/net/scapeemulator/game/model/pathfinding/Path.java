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

import java.util.Deque;
import java.util.LinkedList;

import net.scapeemulator.game.model.Position;

/**
 * Created by Graham
 */
public final class Path {

    /**
     * The queue of positions.
     */
    private Deque<Position> tiles = new LinkedList<>();

    /**
     * Creates an empty path.
     */
    public Path() {}

    /**
     * Adds a Position onto the queue.
     * @param p The Position to add.
     */
    public void addFirst(Position p) {
        tiles.addFirst(p);
    }
    
    /**
     * Adds a position onto the queue.
     * @param p The position to add.
     */
    public void addLast(Position p) {
        tiles.addLast(p);
    }
    
    /**
     * Peeks at the next tile in the path.
     * @return The next tile.
     */
    public Position peek() {
        return tiles.peek();
    }
    
    /**
     * Polls a position from the path.
     * @return The polled position. 
     */
    public Position poll() {
        return tiles.poll();
    }

    /**
     * Gets if the path is empty.
     * @return If the tile deque is empty.
     */
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    /**
     * Gets the deque backing this path.
     * @return The deque backing this path.
     */
    public Deque<Position> getPoints() {
        return tiles;
    }
}
