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

import net.scapeemulator.game.model.Option;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.player.SlottedItem;
import net.scapeemulator.game.model.player.inventory.Inventory;
import net.scapeemulator.game.util.HandlerContext;

/**
 * Created by Hadyn Richard
 */
public abstract class ItemHandler {

    /**
     * The option that the item handler will be bound to.
     */
    private final Option option;

    /**
     * Constructs a new {@link ItemHandler};
     * @param option The option that the item handler will be bound to.
     */
    public ItemHandler(Option option) {
        this.option = option;
    }

    /**
     * Gets the option that this handler will be for.
     * @return The option.
     */
    public Option getOption() {
        return option;
    }

    public abstract void handle(Player player, Inventory inventory, SlottedItem item, String option, HandlerContext context);
}
