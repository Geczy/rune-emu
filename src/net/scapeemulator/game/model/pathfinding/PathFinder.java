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

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.mob.Mob;
import net.scapeemulator.game.model.player.Player;

/**
 * Created by Hadyn Richard
 */
public abstract class PathFinder {
    
    public Path find(Mob mob, int destX, int destY) {

        /* Get the current position of the player */
        Position position = mob.getPosition();

        /* Get the scene base x and y coordinates */
        int baseLocalX = position.getBaseLocalX(), baseLocalY = position.getBaseLocalY();

        /* Calculate the local x and y coordinates */
        int destLocalX = destX - baseLocalX, destLocalY = destY - baseLocalY;

        return find(new Position(baseLocalX, baseLocalY, position.getHeight()), 104, 104, position.getLocalX(), position.getLocalY(), destLocalX, destLocalY, mob.getSize());
    }

    public Path find(Position position, int width, int length, int srcX, int srcY, int destX, int destY) {
        return find(position, width, length, srcX, srcY, destX, destY, 1);
    }
    
    public abstract Path find(Position position, int width, int length, int srcX, int srcY, int destX, int destY, int size);
}
