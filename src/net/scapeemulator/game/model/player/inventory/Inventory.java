package net.scapeemulator.game.model.player.inventory;

import java.util.ArrayList;
import java.util.List;

import net.scapeemulator.game.model.player.Item;

public final class Inventory {

	public enum StackMode {
		ALWAYS,
		STACKABLE_ONLY;
	}

	private final StackMode stackMode;
	private final Item[] items;
	private final List<InventoryListener> listeners = new ArrayList<>();

	public Inventory(int slots) {
		this(slots, StackMode.STACKABLE_ONLY);
	}

	public Inventory(int slots, StackMode stackMode) {
		this.stackMode = stackMode;
		this.items = new Item[slots];
	}

	public Inventory(Inventory inventory) {
		this.stackMode = inventory.stackMode;
		this.items = inventory.toArray();
	}

	public Item[] toArray() {
		Item[] array = new Item[items.length];
		System.arraycopy(items, 0, array, 0, items.length);
		return array;
	}

	public void addListener(InventoryListener listener) {
		listeners.add(listener);
	}

	public void removeListener(InventoryListener listener) {
		listeners.remove(listener);
	}

	public void removeListeners() {
		listeners.clear();
	}

	public void refresh() {
		fireItemsChanged();
	}

	public Item get(int slot) {
		checkSlot(slot);
		return items[slot];
	}

	public void set(int slot, Item item) {
		checkSlot(slot);
		items[slot] = item;
		fireItemChanged(slot);
	}

	public void swap(int slot1, int slot2) {
		checkSlot(slot1);
		checkSlot(slot2);

		Item tmp = items[slot1];
		items[slot1] = items[slot2];
		items[slot2] = tmp;

		fireItemChanged(slot1);
		fireItemChanged(slot2);
	}

	public void reset(int slot) {
		set(slot, null);
	}

	public Item add(Item item) {
		return add(item, -1);
	}

	public Item add(Item item, int preferredSlot) {
		int id = item.getId();
		boolean stackable = isStackable(item);
		if (stackable) {
			/* try to add this item to an existing stack */
			int slot = slotOf(id);
			if (slot != -1) {
				Item other = items[slot];
				long total = (long) other.getAmount() + item.getAmount();
				int amount;

				/* check if there are too many items in the stack */
				Item remaining = null;
				if (total > Integer.MAX_VALUE) {
					amount = Integer.MAX_VALUE;
					remaining = new Item(id, (int) (total - amount));
					fireCapacityExceeded();
				} else {
					amount = (int) total;
				}

				/* update stack and return any remaining items */
				set(slot, new Item(item.getId(), amount));
				return remaining;
			}

			/* try to add this item to the preferred slot */
			if (preferredSlot != -1) {
				checkSlot(preferredSlot);
				if (items[preferredSlot] == null) {
					set(preferredSlot, item);
					return null;
				}
			}

			/* try to add this item to any slot */
			for (slot = 0; slot < items.length; slot++) {
				if (items[slot] == null) {
					set(slot, item);
					return null;
				}
			}

			/* give up */
			fireCapacityExceeded();
			return item;
		} else {
			final Item single = new Item(id, 1);
			int remaining = item.getAmount();

			if (remaining == 0)
				return null;

			/* try to first place item at the preferred slot */
			if (preferredSlot != -1) {
				checkSlot(preferredSlot);
				if (items[preferredSlot] == null) {
					set(preferredSlot, single);
					remaining--;
				}
			}

			if (remaining == 0)
				return null;

			/* place any subsequent remaining items wherever space is available */
			for (int slot = 0; slot < items.length; slot++) {
				if (items[slot] == null) {
					set(slot, single);
					remaining--;
				}

				if (remaining == 0)
					return null;
			}

			/* give up */
			fireCapacityExceeded();
			return new Item(id, remaining);
		}
	}

	public Item remove(Item item) {
		return remove(item, -1);
	}

	public Item remove(Item item, int preferredSlot) {
		int id = item.getId();
		boolean stackable = isStackable(item);

		if (stackable) {
			/* try to remove this item from its stack */
			int slot = slotOf(id);
			if (slot != -1) {
				Item other = items[slot];
				if (other.getAmount() >= item.getAmount()) {
					set(slot, null);
					return new Item(id, other.getAmount());
				} else {
					other = new Item(id, other.getAmount() - item.getAmount());
					set(slot, other);
					return item;
				}
			}

			return null;
		} else {
			int removed = 0;

			/* try to remove the item from the preferred slot first */
			if (preferredSlot != -1) {
				checkSlot(preferredSlot);
				if (items[preferredSlot].getId() == id) {
					set(preferredSlot, null);

					if (++removed >= item.getAmount())
						return new Item(id, removed);
				}
			}

			/* try other slots */
			for (int slot = 0; slot < items.length; slot++) {
				Item other = items[slot];
				if (other != null && other.getId() == id) {
					set(slot, null);

					if (++removed >= item.getAmount())
						return new Item(id, removed);
				}
			}

			return removed == 0 ? null : new Item(id, removed);
		}
	}

	public void shift() {
		int destSlot = 0;

		for (int slot = 0; slot < items.length; slot++) {
			Item item = items[slot];
			if (item != null) {
				items[destSlot++] = item;
			}
		}

		for (int slot = destSlot; slot < items.length; slot++)
			items[slot] = null;

		fireItemsChanged();
	}

	public void empty() {
		for (int slot = 0; slot < items.length; slot++)
			items[slot] = null;

		fireItemsChanged();
	}

	public boolean isEmpty() {
		for (int slot = 0; slot < items.length; slot++)
			if (items[slot] != null)
				return false;

		return true;
	}

	public int freeSlots() {
		int slots = 0;
		for (int slot = 0; slot < items.length; slot++)
			if (items[slot] == null)
				slots++;

		return slots;
	}

	public int slotOf(int id) {
		for (int slot = 0; slot < items.length; slot++) {
			Item item = items[slot];
			if (item != null && item.getId() == id)
				return slot;
		}

		return -1;
	}

	public boolean contains(int id) {
		return slotOf(id) != -1;
	}

	private void fireItemChanged(int slot) {
		for (InventoryListener listener : listeners)
			listener.itemChanged(this, slot, items[slot]);
	}

	private void fireItemsChanged() {
		for (InventoryListener listener : listeners)
			listener.itemsChanged(this);
	}

	public void fireCapacityExceeded() {
		for (InventoryListener listener : listeners)
			listener.capacityExceeded(this);
	}

	private boolean isStackable(Item item) {
		if (stackMode == StackMode.ALWAYS)
			return true;

		return item.getDefinition().isStackable();
	}
    
    public boolean check(int slot, int itemId) {
        return get(slot) != null && get(slot).getId() == itemId;
    }

	private void checkSlot(int slot) {
		if (slot < 0 || slot >= items.length)
			throw new IndexOutOfBoundsException("slot out of range");
	}

}
