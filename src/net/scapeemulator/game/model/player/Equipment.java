package net.scapeemulator.game.model.player;

import net.scapeemulator.game.model.player.EquipmentDefinition.WeaponClass;
import net.scapeemulator.game.model.player.inventory.Inventory;
import net.scapeemulator.game.msg.impl.inter.InterfaceTextMessage;

public final class Equipment {

	public static final int HEAD = 0;
	public static final int CAPE = 1;
	public static final int NECK = 2;
	public static final int WEAPON = 3;
	public static final int BODY = 4;
	public static final int SHIELD = 5;
	public static final int LEGS = 7;
	public static final int HANDS = 9;
	public static final int FEET = 10;
	public static final int RING = 12;
	public static final int AMMO = 13;

	public static void remove(Player player, int slot) {
		Inventory inventory = player.getInventory();
		Inventory equipment = player.getEquipment();

		Item item = equipment.get(slot);
		if (item == null)
			return;

		Item remaining = inventory.add(item);
		equipment.set(slot, remaining);

		if (slot == WEAPON && remaining == null) {
			weaponChanged(player);
		}
	}

	public static void equip(Player player, int slot) {
		Inventory inventory = player.getInventory();
		Inventory equipment = player.getEquipment();
		Item originalWeapon = equipment.get(WEAPON);

		Item item = inventory.get(slot);
		if (item == null)
			return;

		EquipmentDefinition def = item.getEquipmentDefinition();
		if (def == null)
			return;

		int targetSlot = def.getSlot();

		boolean unequipShield = def.getSlot() == WEAPON && def.isTwoHanded() && equipment.get(SHIELD) != null;
		boolean unequipWeapon = targetSlot == SHIELD && equipment.get(WEAPON) != null && equipment.get(WEAPON).getEquipmentDefinition().isTwoHanded();
		boolean topUpStack = item.getDefinition().isStackable() && item.getId() == equipment.get(targetSlot).getId();
		boolean drainStack = equipment.get(targetSlot) != null && equipment.get(targetSlot).getDefinition().isStackable() && inventory.contains(equipment.get(targetSlot).getId()); 

		if ((unequipShield || unequipWeapon) && inventory.freeSlots() == 0) {
			inventory.fireCapacityExceeded();
			return;
		}

		if (topUpStack) {
			Item remaining = equipment.add(item);
			inventory.set(slot, remaining);
		} else {
			if (drainStack) {
				Item remaining = inventory.add(equipment.get(targetSlot));
				equipment.set(targetSlot, remaining);
				if (remaining != null)
					return;
			}

			inventory.remove(item, slot);

			Item other = equipment.get(targetSlot);
			if (other != null) {
				inventory.add(other);
			}

			equipment.set(targetSlot, item);
		}

		if (unequipShield) {
			Item remaining = inventory.add(equipment.get(SHIELD));
			equipment.set(SHIELD, remaining);
		} else if (unequipWeapon) {
			Item remaining = inventory.add(equipment.get(WEAPON));
			equipment.set(WEAPON, remaining);
		}

		Item weapon = equipment.get(WEAPON);
		boolean weaponChanged = false;
		if (originalWeapon == null && weapon != null)
			weaponChanged = true;
		else if (weapon == null && originalWeapon != null)
			weaponChanged = true;
		else if (originalWeapon != null && weapon != null && originalWeapon.getId() != weapon.getId())
			weaponChanged = true;

		if (weaponChanged) {
			weaponChanged(player);
		}
	}

	private static void weaponChanged(Player player) {
		// TODO try to keep the same attack style if possible?
		player.getSettings().setAttackStyle(0);
		openAttackTab(player);
	}

	public static void openAttackTab(Player player) {
		Item weapon = player.getEquipment().get(WEAPON);

		String name;
		WeaponClass weaponClass;
		if (weapon != null) {
			name = weapon.getDefinition().getName();
			weaponClass = weapon.getEquipmentDefinition().getWeaponClass();
		} else {
			name = "Unarmed";
			weaponClass = WeaponClass.UNARMED;
		}

		int tab = weaponClass.getTab();
		//player.getInterfaceSet().openTab(Tab.ATTACK, tab);
		player.send(new InterfaceTextMessage(tab, 0, name));
	}

	private Equipment() {
		/* empty */
	}

}
