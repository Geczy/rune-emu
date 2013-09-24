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

import net.scapeemulator.game.model.definition.ItemDefinitions;
import net.scapeemulator.game.model.Option;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.player.SlottedItem;
import net.scapeemulator.game.model.player.inventory.Inventory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.scapeemulator.game.util.HandlerContext;

/**
 * Created by Hadyn Richard
 */
public final class ItemDispatcher {

    /**
     * The mapping for all of the handler lists.
     */
    private Map<Option, List<ItemHandler>> handlerLists = new HashMap<>();

    /**
     * Constructs a new {@link ItemDispatcher};
     */
    public ItemDispatcher() {
        for(Option option : Option.values()) {
            if(option.equals(Option.ALL)) {
                continue;
            }
            handlerLists.put(option, new LinkedList<ItemHandler>());
        }
    }

    /**
     * Binds a handler to this dispatcher.
     * @param handler The handler to bind.
     */
    public void bind(ItemHandler handler) {
        if(handler.getOption().equals(Option.ALL)) {
            for(Map.Entry<Option, List<ItemHandler>> entry : handlerLists.entrySet()) {
                entry.getValue().add(handler);
            }
        } else {
            List<ItemHandler> list = handlerLists.get(handler.getOption());
            list.add(handler);
        }
    }
    
    public void unbindAll() {
        for(List<?> list : handlerLists.values()) {
            list.clear();
        }
    }
    
    private static boolean validateInventory(Inventory inventory, int id, int slot) {
        return inventory.get(slot) != null && inventory.get(slot).getId() == id;
    }

    /**
     * Gets the name of the option for an item.
     * @param id  The item id.
     * @param option The option.
     * @return The option name.
     */
    private static String getOptionName(int id, Option option) {
        String optionName = ItemDefinitions.forId(id).getInventoryOptions()[option.toInteger()];
        return optionName == null ? "null" : optionName.toLowerCase();
    }


    public void handle(Player player, int id, int slot, int hash, Option option) {
        List<ItemHandler> handlers = handlerLists.get(option);
        if(handlers != null) {

            Inventory inventory = player.getInventorySet().get(hash);
            if(inventory == null || !validateInventory(inventory, id, slot)) {
                return;
            }

            SlottedItem slottedItem = new SlottedItem(slot, inventory.get(slot));
            String optionName = getOptionName(id, option);
            
            HandlerContext context = new HandlerContext();

            for(ItemHandler handler : handlers) {

                /* Handle the message parameters */
                handler.handle(player, inventory, slottedItem, optionName, context);
                
                if(context.doStop()) {
                    break;
                }
            }
        }
    }
}
