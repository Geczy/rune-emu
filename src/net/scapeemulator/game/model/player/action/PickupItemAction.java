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

import net.scapeemulator.game.model.*;
import net.scapeemulator.game.model.definition.ItemDefinitions;
import net.scapeemulator.game.model.player.Item;
import net.scapeemulator.game.task.DistancedAction;
import net.scapeemulator.game.model.grounditem.GroundItemList.GroundItem;
import net.scapeemulator.game.model.player.Player;

/**
 * Created by Hadyn Richard
 */
public final class PickupItemAction extends DistancedAction<Player> {
    
    private final Position position;
    private final int itemId;
    private final Player player;
    
    public PickupItemAction(Player player, int itemId, Position position) {
        super(1, true, player, position, 0);
        this.player = player;
        this.position = position;
        this.itemId = itemId;
    }

    @Override
    public void executeAction() {
        boolean stackable = ItemDefinitions.forId(itemId).isStackable();
        if(player.getGroundItems().contains(itemId, position)) {
            GroundItem groundItem = player.getGroundItems().get(itemId, position);
            
            /* if the inventory is full (or the stack) will return a non-null value */
            Item remaining = player.getInventory().add(groundItem.toItem());
            if(remaining != null) {
                if(stackable) {
                   groundItem.setAmount(remaining.getAmount());
                }
                return;
            }

            /* remove the ground item from the list */
            player.getGroundItems().remove(itemId, position);

            /* if the item was found and it isnt stackable, return */
            if(!stackable) {
                return;
            }
        }
        
        if(World.getWorld().getGroundItems().contains(itemId, position)) {
            GroundItem groundItem = World.getWorld().getGroundItems().get(itemId, position);

            /* if the inventory is full (or the stack) will return a non-null value */
            Item remaining = player.getInventory().add(groundItem.toItem());
            if(remaining != null) {
                if(stackable) {
                    groundItem.setAmount(remaining.getAmount());
                }
                return;
            }

            /* remove the ground item from the list */
            World.getWorld().getGroundItems().remove(itemId, position);
        }

        stop();
    }
}
