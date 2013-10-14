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

package net.scapeemulator.game.grounditem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.scapeemulator.game.model.Option;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.definition.ItemDefinitions;
import net.scapeemulator.game.model.grounditem.GroundItems;
import net.scapeemulator.game.model.player.Player;

/**
 * Created by Hadyn Richard
 */
public final class GroundItemDispatcher {

    /**
     * The mapping for all of the handler lists.
     */
    private Map<Option, List<GroundItemHandler>> handlerLists = new HashMap<>();

    /**
     * Constructs a new {@link GroundItemDispatcher};
     */
    public GroundItemDispatcher() {
        for(Option option : Option.values()) {
            if(option.equals(Option.ALL)) {
                continue;
            }
            handlerLists.put(option, new LinkedList<GroundItemHandler>());
        }

        /* Bind each of the handlers */
        bind(new TakeGroundItemHandler());
    }

    /**
     * Binds a handler to this dispatcher.
     * @param handler The handler to bind.
     */
    public void bind(GroundItemHandler handler) {
        if(handler.getOption().equals(Option.ALL)) {
            for(Entry<Option, List<GroundItemHandler>> entry : handlerLists.entrySet()) {
                entry.getValue().add(handler);
            }
        } else {
            List<GroundItemHandler> list = handlerLists.get(handler.getOption());
            list.add(handler);
        }
    }

    /**
     * Gets the name of the option for an item.
     * @param id  The item id.
     * @param option The option.
     * @return The option name.
     */
    private String getOptionName(int id, Option option) {
        String optionName = ItemDefinitions.forId(id).getGroundOptions()[option.toInteger()];
        return optionName == null ? "null" : optionName.toLowerCase();
    }

    /**
     * Handles the parameters of a ground item message.
     * @param player The player that the message came from.
     * @param id The item id.
     * @param position The position of the ground item.
     * @param option The option that was selected.
     */
    public void handle(Player player, int id, Position position, Option option) {
        List<GroundItemHandler> handlers = handlerLists.get(option);
        if(handlers != null) {

            /* Check if the ground item exists before going further */
            if(!GroundItems.exists(player, id, position)) {
                return;
            }

            String optionName = getOptionName(id, option);
            for(GroundItemHandler handler : handlers) {

                /* Handle the message parameters */
                if(!handler.handle(player, position, id, optionName)) {
                    break;
                }
            }
        }
    }
}
