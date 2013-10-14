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

package net.scapeemulator.game.model.npc.stateful.handler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.scapeemulator.game.model.Area;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.mob.Direction;
import net.scapeemulator.game.model.npc.NPC;
import net.scapeemulator.game.model.npc.stateful.StateHandler;

/**
 * Created by Hadyn Richard
 */
public final class RandomWalkStateHandler extends StateHandler {
    
    private final static Random random = new Random();

    @Override
    public void handle(NPC npc) {

        /* Get the amount of steps that the NPC will walk if possible */
        int iterations = 1;
        if(random.nextInt(10) < 5) {
            iterations = 2;
        }

        Position currentPosition = npc.getPosition();

        Set<Position> oldPositions = new HashSet<>();
        oldPositions.add(currentPosition);
        
        for(int i = 0; i < iterations; i++) {

            List<Position> positions = Direction.getNearbyTraversableTiles(currentPosition, npc.getSize());

            Area bounds = npc.getBounds();

            /* Remove all the positions that are not within bounds or previously used */
            Iterator<Position> iterator = positions.iterator();
            while(iterator.hasNext()) {
                Position position = iterator.next();
                if(!bounds.withinArea(position, npc.getSize()) ||
                    oldPositions.contains(position)) {
                    iterator.remove();
                }
            }

            Position[] array = positions.toArray(new Position[0]);

            if(array.length > 0) {
                Position position = array[random.nextInt(array.length)];
                npc.getWalkingQueue().addPoint(position);
                currentPosition = position;
            }
        }
    }
}
