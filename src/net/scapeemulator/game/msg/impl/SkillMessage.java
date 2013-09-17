package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class SkillMessage extends Message {

	private final int skill, level, experience;

	public SkillMessage(int skill, int level, int experience) {
		this.skill = skill;
		this.level = level;
		this.experience = experience;
	}

	public int getSkill() {
		return skill;
	}

	public int getLevel() {
		return level;
	}

	public int getExperience() {
		return experience;
	}

}
