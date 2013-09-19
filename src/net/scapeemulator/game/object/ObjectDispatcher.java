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

package net.scapeemulator.game.object;

import net.scapeemulator.game.model.*;
import net.scapeemulator.game.model.definition.ObjectDefinitions;
import net.scapeemulator.game.model.object.GroundObjectList.GroundObject;
import net.scapeemulator.game.model.player.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public final class ObjectDispatcher {

    /**
     * The mapping for all of the handler lists.
     */
    private Map<Option, List<ObjectHandler>> handlerLists = new HashMap<>();

    /**
     * Constructs a new {@link ObjectDispatcher};
     */
    public ObjectDispatcher() {
        for(Option option : Option.values()) {
            if(option.equals(Option.ALL)) {
                continue;
            }
            handlerLists.put(option, new LinkedList<ObjectHandler>());
        }
    }

    public void bind(ObjectHandler handler) {
        if(handler.getOption().equals(Option.ALL)) {
            for(Map.Entry<Option, List<ObjectHandler>> entry : handlerLists.entrySet()) {
                entry.getValue().add(handler);
            }
        } else {
            List<ObjectHandler> list = handlerLists.get(handler.getOption());
            list.add(handler);
        }
    }
    
    public void unbindAll() {
        for(List<ObjectHandler> list : handlerLists.values()) {
            list.clear();
        }
    }

    private static String getOptionName(int id, Option option) {
        String optionName = ObjectDefinitions.forId(id).getOptions()[option.toInteger()];
        return optionName == null ? "null" : optionName.toLowerCase();
    }

    public void handle(Player player, int id, Position position, Option option) {
        List<ObjectHandler> handlers = handlerLists.get(option);
        if(handlers != null) {

            GroundObject object = World.getWorld().getGroundObjects().get(id, position);
            if(object == null || object.isHidden()) {
                return;
            }
            
            System.out.println("id: " + id + ", rotation: " + object.getRotation() + " , option: " + option + ", type: " + object.getType());

            String optionName = getOptionName(id, option);

            for(ObjectHandler handler : handlers) {

                /* Handle the message parameters */
                if(handler.handle(player, object, optionName)) {
                    break;
                }
            }
        }
    }
}
