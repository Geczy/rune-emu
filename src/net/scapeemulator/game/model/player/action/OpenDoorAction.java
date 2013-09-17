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

package net.scapeemulator.game.model.player.action;

import net.scapeemulator.game.model.Door;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.task.DistancedAction;

/**
 * Created by Hadyn Richard
 */
public final class OpenDoorAction extends DistancedAction<Player> {

    private final Door door;
    private final boolean open;
    private final Player player;
    private boolean executed;

    public OpenDoorAction(Player player, Position position, Door door, boolean open) {
        super(1, true, player, position, 1);
        this.player = player;
        this.door = door;
        this.open = open;
    }

    @Override
    public void executeAction() {

        /* If the player is still walking, dont execute the action and assume that next time he will be done wallking */
        if(!executed && !player.getWalkingQueue().isEmpty()) {
            executed = true;
            return;
        }
        
        player.turnToPosition(position);

        if(open) {
            door.open();
        } else {
            door.close();
        }
        
        stop();
    }
}
