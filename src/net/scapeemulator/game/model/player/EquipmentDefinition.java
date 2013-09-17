package net.scapeemulator.game.model.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class EquipmentDefinition {

	public enum WeaponClass {
		AXE(Interface.ATTACK_AXE),
		MAUL(Interface.ATTACK_MAUL),
		BOW(Interface.ATTACK_BOW),
		CLAWS(Interface.ATTACK_CLAWS),
		LONGBOW(Interface.ATTACK_LONGBOW),
		FIXED_DEVICE(Interface.ATTACK_FIXED_DEVICE),
		GODSWORD(Interface.ATTACK_GODSWORD),
		SWORD(Interface.ATTACK_SWORD),
		PICKAXE(Interface.ATTACK_PICKAXE),
		HALBERD(Interface.ATTACK_HALBERD),
		STAFF(Interface.ATTACK_STAFF),
		SCYTHE(Interface.ATTACK_SCYTHE),
		SPEAR(Interface.ATTACK_SPEAR),
		MACE(Interface.ATTACK_MACE),
		DAGGER(Interface.ATTACK_DAGGER),
		MAGIC_STAFF(Interface.ATTACK_MAGIC_STAFF),
		THROWN(Interface.ATTACK_THROWN),
		UNARMED(Interface.ATTACK_UNARMED),
		WHIP(Interface.ATTACK_WHIP);

		private final int tab;

		private WeaponClass(int tab) {
			this.tab = tab;
		}

		public int getTab() {
			return tab;
		}
	}

	public static final int FLAG_TWO_HANDED = 0x1;
	public static final int FLAG_FULL_HELM = 0x2;
	public static final int FLAG_FULL_MASK = 0x4;
	public static final int FLAG_FULL_BODY = 0x8;

	private static final Logger logger = LoggerFactory.getLogger(EquipmentDefinition.class);
	private static final Map<Integer, EquipmentDefinition> definitions = new HashMap<>();

	public static void init() throws IOException {
		try (DataInputStream reader = new DataInputStream(new FileInputStream("data/game/equipment.dat"))) {
			int id, nextEquipmentId = 0;
			while ((id = reader.readShort()) != -1) {
				int flags = reader.read() & 0xFF;
				int slot = reader.read() & 0xFF;
				int stance = 0, weaponClass = 0;
				if (slot == Equipment.WEAPON) {
					stance = reader.readShort() & 0xFFFF;
					weaponClass = reader.read() & 0xFF;
				}

				EquipmentDefinition equipment = new EquipmentDefinition();
				equipment.id = id;
				equipment.equipmentId = nextEquipmentId++;
				equipment.slot = slot;
				equipment.twoHanded = (flags & FLAG_TWO_HANDED) != 0;
				equipment.fullHelm = (flags & FLAG_FULL_HELM) != 0;
				equipment.fullMask = (flags & FLAG_FULL_MASK) != 0;
				equipment.fullBody = (flags & FLAG_FULL_BODY) != 0;
				if (slot == Equipment.WEAPON) {
					equipment.stance = stance;
					equipment.weaponClass = WeaponClass.values()[weaponClass];
				}

				definitions.put(id, equipment);
			}

			logger.info("Loaded " + definitions.size() + " equipment definitions.");
		}
	}

	public static EquipmentDefinition forId(int id) {
		return definitions.get(id);
	}

	private int id, equipmentId, slot, stance;
	private boolean fullBody, fullMask, fullHelm, twoHanded;
	private WeaponClass weaponClass;

	public int getId() {
		return id;
	}

	public int getEquipmentId() {
		return equipmentId;
	}

	public int getSlot() {
		return slot;
	}

	public boolean isFullBody() {
		if (slot != Equipment.BODY)
			throw new IllegalStateException();

		return fullBody;
	}

	public boolean isFullMask() {
		if (slot != Equipment.HEAD)
			throw new IllegalStateException();

		return fullMask;
	}

	public boolean isFullHelm() {
		if (slot != Equipment.HEAD)
			throw new IllegalStateException();

		return fullHelm;
	}

	public boolean isTwoHanded() {
		if (slot != Equipment.WEAPON)
			throw new IllegalStateException();

		return twoHanded;
	}

	public int getStance() {
		if (slot != Equipment.WEAPON)
			throw new IllegalStateException();

		return stance;
	}

	public WeaponClass getWeaponClass() {
		if (slot != Equipment.WEAPON)
			throw new IllegalStateException();

		return weaponClass;
	}

}
