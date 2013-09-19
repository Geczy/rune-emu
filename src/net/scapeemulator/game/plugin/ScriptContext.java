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

package net.scapeemulator.game.plugin;

import net.scapeemulator.game.button.ButtonDispatcher;
import net.scapeemulator.game.button.ButtonHandler;
import net.scapeemulator.game.command.CommandDispatcher;
import net.scapeemulator.game.command.CommandHandler;
import net.scapeemulator.game.item.*;
import net.scapeemulator.game.object.ObjectDispatcher;
import net.scapeemulator.game.object.ObjectHandler;

import java.util.LinkedList;
import java.util.List;
import net.scapeemulator.game.player.PlayerDispatcher;
import net.scapeemulator.game.player.PlayerHandler;

/**
 * Created by Hadyn Richard
 */
public final class ScriptContext {

    /**
     * The list of button handlers.
     */
    private List<ButtonHandler> buttonHandlers = new LinkedList<>();

    /**
     * The list of command handlers.
      */
    private List<CommandHandler> commandHandlers = new LinkedList<>();

    /**
     * The list of item on item handlers.
     */
    private List<ItemOnItemHandler> itemOnItemHandlers = new LinkedList<>();

    /**
     * The list of item handlers.
     */
    private List<ItemHandler> itemHandlers = new LinkedList<>();

    /**
     * The list of item on object handlers.
     */
    private List<ItemOnObjectHandler> itemOnObjectHandlers = new LinkedList<>();

    /**
     * The list of object handlers.
     */
    private List<ObjectHandler> objectHandlers = new LinkedList<>();
    
    /**
     * The list of player handlers.
     */
    private List<PlayerHandler> playerHandlers = new LinkedList<>();

    /**
     * Constructs a new {@link ScriptContext};
     */
    public ScriptContext() {}

    /**
     * Adds a button dispatcher handler to the list of handlers.
     * @param handler The button handler to add.
     */
    public void addButtonHandler(ButtonHandler handler) {
        buttonHandlers.add(handler);
    }

    /**
     * Decorates a button dispatcher with all the button handlers registered to the context.
     * @param dispatcher The dispatcher to decorate.
     */
    public void decorateButtonDispatcher(ButtonDispatcher dispatcher) {
        for(ButtonHandler handler : buttonHandlers) {
            dispatcher.bind(handler);
        }
    }

    /**
     * Adds a button dispatcher handler to the list of decorators.
     * @param handler The button handler to add.
     */
    public void addCommandHandler(CommandHandler handler) {
        commandHandlers.add(handler);
    }

    /**
     * Decorates a button dispatcher with all the button handlers registered to the context.
     * @param dispatcher The dispatcher to decorate.
     */
    public void decorateCommandDispatcher(CommandDispatcher dispatcher) {
        for(CommandHandler handler : commandHandlers) {
            dispatcher.bind(handler);
        }
    }

    /**
     * Adds an item on item handler to the list of handlers.
     * @param handler The handler to add.
     */
    public void addItemOnItemHandler(ItemOnItemHandler handler) {
        itemOnItemHandlers.add(handler);
    }

    /**
     * Decorates a item on item dispatcher with all the item on item handlers registered to the context.
     * @param dispatcher The dispatcher to decorate.
     */
    public void decorateItemOnItemDispatcher(ItemOnItemDispatcher dispatcher) {
        for(ItemOnItemHandler handler : itemOnItemHandlers) {
            dispatcher.bind(handler);
        }
    }

    /**
     * Adds an item handler to the list of handlers.
     * @param handler The handler to add.
     */
    public void addItemHandler(ItemHandler handler) {
        itemHandlers.add(handler);
    }

    /**
     * Decorates an item dispatcher with all the item handlers registered to the context.
     * @param dispatcher The dispatcher to decorate.
     */
    public void decorateItemDispatcher(ItemDispatcher dispatcher) {
       for(ItemHandler handler : itemHandlers) {
           dispatcher.bind(handler);
       }
    }

    /**
     * Adds an item on object handler to the list of handlers.
     * @param handler The handler to add.
     */
    public void addItemOnObjectHandler(ItemOnObjectHandler handler) {
        itemOnObjectHandlers.add(handler);
    }

    /**
     * Decorates an item on object dispatcher with all the item on object handlers registered to the context.
     * @param dispatcher The dispatcher to decorate.
     */
    public void decorateItemOnObjectDispatcher(ItemOnObjectDispatcher dispatcher) {
        for(ItemOnObjectHandler handler : itemOnObjectHandlers) {
            dispatcher.bind(handler);
        }
    }

    /**
     * Adds an object handler to the list of handlers.
     * @param handler The handler to add.
     */
    public void addObjectHandler(ObjectHandler handler) {
        objectHandlers.add(handler);
    }

    /**
     * Decorates an object dispatcher with all the object handlers registered to the context.
     * @param dispatcher The dispatcher to decorate.
     */
    public void decorateObjectDispatcher(ObjectDispatcher dispatcher) {
        for(ObjectHandler handler : objectHandlers) {
            dispatcher.bind(handler);
        }
    }
    
    /**
     * Adds an player handler to the list of handlers.
     * @param handler The handler to add.
     */
    public void addPlayerHandler(PlayerHandler handler) {
        playerHandlers.add(handler);
    }

    /**
     * Decorates an player dispatcher with all the player handlers registered to the context.
     * @param dispatcher The dispatcher to decorate.
     */
    public void decoratePlayerDispatcher(PlayerDispatcher dispatcher) {
        for(PlayerHandler handler : playerHandlers) {
            dispatcher.bind(handler);
        }
    }
}