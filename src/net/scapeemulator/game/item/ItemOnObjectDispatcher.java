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

package net.scapeemulator.game.item;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.World;
import net.scapeemulator.game.model.object.GroundObjectList;
import net.scapeemulator.game.model.object.GroundObjectList.GroundObject;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.player.SlottedItem;
import net.scapeemulator.game.model.player.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public final class ItemOnObjectDispatcher {
    
    private Map<Integer, ItemOnObjectHandler> handlers = new HashMap<>();
    
    public ItemOnObjectDispatcher() {}
    
    public void bind(ItemOnObjectHandler handler) {
        int hash = getHash(handler.getItemId(), handler.getObjectId());
        handlers.put(hash, handler);
    }
    
    public void unbindAll() {
        handlers.clear();
    }

    public void handle(Player player, int objectId, Position position, int hash, int itemId, int slot) {

        GroundObjectList groundObjects = World.getWorld().getGroundObjects();

        GroundObject object = groundObjects.get(objectId, position);
        if(object == null) {
            return;
        }

        Inventory inventory = player.getInventorySet().get(hash);

        /* Validate the inventory */
        if(inventory == null || !inventory.check(slot, itemId)) {
            return;
        }

        SlottedItem item = new SlottedItem(slot, inventory.get(slot));
        ItemOnObjectHandler handler = handlers.get(getHash(itemId, objectId));

        if(handler != null) {
            handler.handle(player, object, inventory, item);
        }
    }
    
    private static int getHash(int itemId, int objectId) {
        return itemId << 16 | objectId;
    }
}
