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

package net.scapeemulator.game.model.player;

import net.scapeemulator.game.model.player.inventory.Inventory;
import net.scapeemulator.game.model.player.inventory.InventoryAppearanceListener;
import net.scapeemulator.game.model.player.inventory.InventoryFullListener;
import net.scapeemulator.game.model.player.inventory.InventoryMessageListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public final class InventorySet {

    /**
     * The equipment inventory hash.
     */
    public static final int EQUIPMENT_HASH = Interface.EQUIPMENT << 16 | Interface.EQUIPMENT_CONAINER;

    /**
     * The inventory container hash.
     */
    public static final int INVENTORY_HASH = Interface.INVENTORY << 16 | Interface.INVENTORY_CONTAINER;

    /**
     * Each of the inventories registered to the inventory set.
     */
    private final Map<Integer, Inventory> inventories = new HashMap<>();

    /**
     * Constructs a new {@link InventorySet};
     */
    public InventorySet(Player player) {

        /* Initialize and register the equipment to the set */
        Inventory equipment = new Inventory(14);
        equipment.addListener(new InventoryMessageListener(player, Interface.EQUIPMENT, Interface.EQUIPMENT_CONAINER, 94));
        equipment.addListener(new InventoryFullListener(player, "equipment"));
        equipment.addListener(new InventoryAppearanceListener(player));
        register(equipment, EQUIPMENT_HASH);

        /* Initialize and register the inventory to the set */
        Inventory inventory = new Inventory(28);
        inventory.addListener(new InventoryMessageListener(player, Interface.INVENTORY, Interface.INVENTORY_CONTAINER, 93));
        inventory.addListener(new InventoryFullListener(player, "inventory"));
        register(inventory, INVENTORY_HASH);
    }

    /**
     * Registers an inventory to the inventory set.
     * @param inventory The inventory to register.
     * @param parent The parent widget id.
     * @param child The child widget id.
     */
    public void register(Inventory inventory, int parent, int child) {
        register(inventory, getHash(parent, child));
    }

    /**
     * Registers an inventory to the inventory set.
     * @param inventory The inventory to register.
     * @param hash The hash of the inventory.
     */
    public void register(Inventory inventory, int hash) {
        inventories.put(hash, inventory);
    }
    
    public Inventory get(int hash) {
        return inventories.get(hash);
    }

    /**
     * Gets the inventory container.
     * @return The inventory.
     */
    public Inventory getInventory() {
        return inventories.get(INVENTORY_HASH);
    }

    /**
     * Gets the equipment inventory.
     * @return The equipment.
     */
    public Inventory getEquipment() {
        return inventories.get(EQUIPMENT_HASH);
    }

    /**
     * Calculates a widget hash.
     * @param parent The parent id.
     * @param child The child id.
     * @return The calculated hash.
     */
    private static int getHash(int parent, int child) {
        return parent << 16 | child;
    }
}
