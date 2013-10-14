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

package net.scapeemulator.game.model.grounditem;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import net.scapeemulator.game.model.Entity;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.definition.ItemDefinitions;
import net.scapeemulator.game.model.player.Item;

/**
 * Created by Hadyn Richard
 */
public final class GroundItemList {

    /**
     * The UID counter for all the ground items that are created.
     */
    private static final AtomicInteger counter = new AtomicInteger(1);

    /**
     * The ground item stacks in this list.
     */
    private final Map<Position, GroundItemStack> stacks = new LinkedHashMap<>();

    /**
     * The list of listeners for this list.
     */
    private final List<GroundItemListener> listeners = new LinkedList<>();

    /**
     * The type of this list.
     */
    private final Type type;

    /**
     * Constructs a new [@link GroundItemList};
     * @param type The type of the list.
     */
    public GroundItemList(Type type) {
        this.type = type;
    }

    /**
     * The two different scopes for a list.
     */
    public enum Type {
        WORLD, LOCAL
    }

    /**
     * The class that represents a stack of ground items at a specific location.
     */
    private class GroundItemStack {

        /**
         * The ground items that are contained in the stack.
         */
        private final List<GroundItem> groundItems = new LinkedList<>();

        /**
         * The position at which the stack is located at.
         */
        private final Position position;

        /**
         * Constructs a new {@link GroundItemStack};
         * @param position  The position of the stack.
         */
        public GroundItemStack(Position position) {
            this.position = position;
        }

        /**
         * Appends a new ground item to the stack.
         * @param itemId The id of the item to append.
         * @param amount The amount of the item.
         */
        public void add(int itemId, int amount) {
            if(ItemDefinitions.forId(itemId).isStackable()) {
                for(GroundItem groundItem : groundItems) {
                    if(groundItem.getItemId() != itemId) {
                        continue;
                    }

                    /* update the ground item */
                    groundItem.setAmount(groundItem.getAmount() + amount);

                    return;
                }
            }

            /* add the ground item to the ground item list */
            GroundItem groundItem = new GroundItem(position, itemId, amount);
            groundItem.setUid(counter.getAndIncrement());
            groundItems.add(groundItem);

            /* alert the listeners a new ground item was created */
            for(GroundItemListener listener : listeners) {
                listener.groundItemCreated(groundItem, type);
            }
        }

        /**
         * Gets if the stack is empty.
         * @return If the ground item map is empty.
         */
        public boolean isEmpty() {
            return groundItems.isEmpty();
        }

        /**
         * Gets the ground item in the list with the specified item id.
         * @param itemId The item id for the ground item to get.
         * @return The found ground item.
         */
        public GroundItem get(int itemId) {
            for(GroundItem groundItem : groundItems) {
                if(groundItem.getItemId() != itemId) {
                    continue;
                }
                return groundItem;
            }
            return null;
        }

        /**
         * Gets if the stack contains a ground item with the specified item id.
         * @param itemId The item id for the ground item to check.
         * @return If the ground item with the item id exists.
         */
        public boolean contains(int itemId) {
            for(GroundItem groundItem : groundItems) {
                if(groundItem.getItemId() != itemId) {
                    continue;
                }
                return true;
            }
            return false;
        }

        /**
         * Removes a ground item from the stack.
         * @param itemId The item id for the ground item to remove.
         * @return The removed ground item.
         */
        public GroundItem remove(int itemId) {
            Iterator<GroundItem> iterator = groundItems.iterator();
            while(iterator.hasNext()) {

                /* Get the next ground item */
                GroundItem groundItem = iterator.next();

                /* Check that its the ground item that we are looking for */
                if(groundItem.getItemId() != itemId) {
                    continue;
                }

                /* Remove the ground item from the stack list */
                iterator.remove();

                /* Alert the listeners a ground item was removed */
                for(GroundItemListener listener : listeners) {
                    listener.groundItemRemoved(groundItem, type);
                }

                return groundItem;
            }

            return null;
        }

        /**
         * Gets the ground items in the stack.
         * @return The ground item list.
         */
        public List<GroundItem> getGroundItems() {
            return groundItems;
        }
    }

    /**
     * The representation of a ground item for this list.
     */
    public class GroundItem extends Entity {

        /**
         * The unique id of the ground item.
         */
        private int uid;

        /**
         * The item id that the ground item represents.
         */
        private int itemId;

        /**
         * The amount of the item that the ground item represents.
         */
        private int amount;

        public GroundItem(Position position, int itemId, int amount) {
            this.position = position;
            this.itemId = itemId;
            this.amount = amount;
        }

        /**
         * Sets the unique id.
         * @param uid The unique id.
         */
        public void setUid(int uid) {
            this.uid = uid;
        }

        /**
         * Gets the unique id.
         * @return The unique id.
         */
        public int getUid() {
            return uid;
        }

        /**
         * Sets the amount of the item.
         * @param newAmount The new amount.
         */
        public void setAmount(int newAmount) {
            if(newAmount != amount) {
                int oldAmount = amount;
                amount = newAmount;

                for(GroundItemListener listener : listeners) {
                    listener.groundItemUpdated(this, oldAmount, type);
                }
            }
        }

        /**
         * Gets the id of the item that the ground item represents.
         * @return The item id.
         */
        public int getItemId() {
            return itemId;
        }

        /**
         * Gets the amount of the item that the ground item represents.
         * @return The item amount.
         */
        public int getAmount() {
            return amount;
        }

        /**
         * Converts the ground item to a immutable item.
         * @return The created immutable item.
         */
        public Item toItem() {
            return new Item(itemId, amount);
        }
    }

    /**
     * Adds a listener for the list.
     */
    public void addListener(GroundItemListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the list.
     */
    public void removeListener(GroundItemListener listener) {
        listeners.remove(listener);
    }

    /**
     * Fires a ground item created event for each ground item in the list.
     */
    public void fireEvents(GroundItemListener listener) {
        for(Entry<Position, GroundItemStack> entry : stacks.entrySet()) {
            for(GroundItem groundItem : entry.getValue().getGroundItems()) {
                listener.groundItemCreated(groundItem, type);
            }
        }
    }

    /**
     * Adds a ground item to the list.
     * @param itemId The id of the item.
     * @param amount The amount of the item.
     * @param position The position of the ground item.
     */
    public void add(int itemId, int amount, Position position) {
        GroundItemStack stack = stacks.get(position);
        if(stack == null) {
            stack = new GroundItemStack(position);
            stacks.put(position, stack);
        }
        stack.add(itemId, amount);
    }

    /**
     * Gets a ground item.
     * @param itemId The item id.
     * @param position The position of the item.
     * @return The ground item.
     */
    public GroundItem get(int itemId, Position position) {
        GroundItemStack stack = stacks.get(position);
        if(stack == null) {
            return null;
        }
        return stack.get(itemId);
    }

    /**
     * Checks if the list contains a ground item.
     * @param itemId The id of the item to check for.
     * @param position The position of the item.
     * @return If the ground item exists.
     */
    public boolean contains(int itemId, Position position) {
        GroundItemStack stack = stacks.get(position);
        if(stack == null) {
            return false;
        }
        return stack.contains(itemId);
    }

    /**
     * Removes a ground item.
     * @param itemId The item id for the ground item.
     * @param position The position of the ground item.
     * @return The removed ground item.
     */
    public GroundItem remove(int itemId, Position position) {
        GroundItemStack stack = stacks.get(position);
        if(stack == null) {
            return null;
        }

        GroundItem groundItem = stack.remove(itemId);

        /* Remove the stack if its empty */
        if(stack.isEmpty()) {
            stacks.remove(position);
        }

        return groundItem;
    }
}
