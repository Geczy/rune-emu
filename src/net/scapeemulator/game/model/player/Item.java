package net.scapeemulator.game.model.player;

import net.scapeemulator.cache.def.ItemDefinition;
import net.scapeemulator.game.model.definition.ItemDefinitions;

public final class Item {

	private final int id, amount;

	public Item(int id) {
		this(id, 1);
	}

	public Item(int id, int amount) {
		if (amount < 0)
			throw new IllegalArgumentException();

		this.id = id;
		this.amount = amount;
	}
    
    public Item add(Item item) {
        if(item.getId() != id) {
            throw new IllegalStateException();
        }
        int newAmount = item.getAmount() + amount;
        return new Item(id, newAmount);
    }

	public int getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (amount != other.amount)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Item.class.getSimpleName() + " [id=" + id + ", amount=" + amount + "]";
	}

	public ItemDefinition getDefinition() {
		return ItemDefinitions.forId(id);
	}

	public EquipmentDefinition getEquipmentDefinition() {
		return EquipmentDefinition.forId(id);
	}

}
