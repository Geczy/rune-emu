package net.scapeemulator.game.update;

import net.scapeemulator.game.model.player.*;
import net.scapeemulator.game.model.player.inventory.Inventory;
import net.scapeemulator.game.msg.impl.PlayerUpdateMessage;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrameBuilder;
import net.scapeemulator.util.Base37Utils;

public final class AppearancePlayerBlock extends PlayerBlock {

	private final String username;
	private final Appearance appearance;
	private final Inventory equipment;
	private final int stance, combat, skill;

	public AppearancePlayerBlock(Player player) {
		super(0x4);
		this.username = player.getUsername();
		this.appearance = player.getAppearance();
		this.equipment = new Inventory(player.getEquipment());
		this.stance = player.getStance();
		this.combat = player.getSkillSet().getCombatLevel();
		this.skill = player.getSkillSet().getTotalLevel();
	}

	@Override
	public void encode(PlayerUpdateMessage message, GameFrameBuilder builder) {
		Gender gender = appearance.getGender();

		GameFrameBuilder propertiesBuilder = new GameFrameBuilder(builder.getAllocator());

		/*
		 * flags field:
		 *   bit 0   - gender (0 = male, 1 = female)
		 *   bit 1   - unused
		 *   bit 2   - show skill level instead of combat level
		 *   bit 3-5 - unknown
		 *   bit 6-7 - unknown
		 */
		int flags = gender.ordinal();
		propertiesBuilder.put(DataType.BYTE, flags);
		propertiesBuilder.put(DataType.BYTE, -1); // pk icon
		propertiesBuilder.put(DataType.BYTE, -1); // prayer icon

		Item item = equipment.get(Equipment.HEAD);
		if (item != null) {
			propertiesBuilder.put(DataType.SHORT, 0x8000 | EquipmentDefinition.forId(item.getId()).getEquipmentId());
		} else {
			propertiesBuilder.put(DataType.BYTE, 0);
		}

		item = equipment.get(Equipment.CAPE);
		if (item != null) {
			propertiesBuilder.put(DataType.SHORT, 0x8000 | EquipmentDefinition.forId(item.getId()).getEquipmentId());
		} else {
			propertiesBuilder.put(DataType.BYTE, 0);
		}

		item = equipment.get(Equipment.NECK);
		if (item != null) {
			propertiesBuilder.put(DataType.SHORT, 0x8000 | EquipmentDefinition.forId(item.getId()).getEquipmentId());
		} else {
			propertiesBuilder.put(DataType.BYTE, 0);
		}

		item = equipment.get(Equipment.WEAPON);
		if (item != null) {
			propertiesBuilder.put(DataType.SHORT, 0x8000 | EquipmentDefinition.forId(item.getId()).getEquipmentId());
		} else {
			propertiesBuilder.put(DataType.BYTE, 0);
		}

		item = equipment.get(Equipment.BODY);
		if (item != null) {
			propertiesBuilder.put(DataType.SHORT, 0x8000 | EquipmentDefinition.forId(item.getId()).getEquipmentId());
		} else {
			propertiesBuilder.put(DataType.SHORT, 0x100 | appearance.getStyle(2));
		}

		item = equipment.get(Equipment.SHIELD);
		if (item != null) {
			propertiesBuilder.put(DataType.SHORT, 0x8000 | EquipmentDefinition.forId(item.getId()).getEquipmentId());
		} else {
			propertiesBuilder.put(DataType.BYTE, 0);
		}

		boolean fullBody = false;
		item = equipment.get(Equipment.BODY);
		if (item != null)
			fullBody = EquipmentDefinition.forId(item.getId()).isFullBody();

		if (!fullBody) {
			propertiesBuilder.put(DataType.SHORT, 0x100 | appearance.getStyle(3));
		} else {
			propertiesBuilder.put(DataType.BYTE, 0);
		}

		item = equipment.get(Equipment.LEGS);
		if (item != null) {
			propertiesBuilder.put(DataType.SHORT, 0x8000 | EquipmentDefinition.forId(item.getId()).getEquipmentId());
		} else {
			propertiesBuilder.put(DataType.SHORT, 0x100 | appearance.getStyle(5));
		}

		boolean fullHelm = false, fullMask = false;
		item = equipment.get(Equipment.HEAD);
		if (item != null) {
			fullHelm = EquipmentDefinition.forId(item.getId()).isFullHelm();
			fullMask = EquipmentDefinition.forId(item.getId()).isFullMask();
		}
		if (!fullHelm && !fullMask) {
			propertiesBuilder.put(DataType.SHORT, 0x100 | appearance.getStyle(0));
		} else {
			propertiesBuilder.put(DataType.BYTE, 0);
		}

		item = equipment.get(Equipment.HANDS);
		if (item != null) {
			propertiesBuilder.put(DataType.SHORT, 0x8000 | EquipmentDefinition.forId(item.getId()).getEquipmentId());
		} else {
			propertiesBuilder.put(DataType.SHORT, 0x100 | appearance.getStyle(4));
		}

		item = equipment.get(Equipment.FEET);
		if (item != null) {
			propertiesBuilder.put(DataType.SHORT, 0x8000 | EquipmentDefinition.forId(item.getId()).getEquipmentId());
		} else {
			propertiesBuilder.put(DataType.SHORT, 0x100 | appearance.getStyle(6));
		}

		item = equipment.get(Equipment.HEAD); // TODO check
		if (gender == Gender.MALE && !fullMask && !fullHelm) {
			propertiesBuilder.put(DataType.SHORT, 0x100 | appearance.getStyle(1));
		} else {
			propertiesBuilder.put(DataType.BYTE, 0);
		}

		for (int i = 0; i < 5; i++) {
			propertiesBuilder.put(DataType.BYTE, appearance.getColor(i));
		}

		propertiesBuilder.put(DataType.SHORT, stance);
		propertiesBuilder.put(DataType.LONG, Base37Utils.encodeBase37(username));
		propertiesBuilder.put(DataType.BYTE, combat);
		if ((flags & 0x4) != 0) {
			propertiesBuilder.put(DataType.SHORT, skill);
		} else {
			propertiesBuilder.put(DataType.BYTE, 0);
			propertiesBuilder.put(DataType.BYTE, 0);
		}
		propertiesBuilder.put(DataType.BYTE, 0);
		/* if the above byte is non-zero, four unknown shorts are written */

		builder.put(DataType.BYTE, DataTransformation.ADD, propertiesBuilder.getLength());
		builder.putRawBuilder(propertiesBuilder);
	}

}
