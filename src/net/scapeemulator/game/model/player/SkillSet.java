package net.scapeemulator.game.model.player;

import java.util.ArrayList;
import java.util.List;

public final class SkillSet {

	public static final double MAXIMUM_EXPERIENCE = 200000000;

	private static int getLevelFromExperience(double xp) {
		int points = 0;
		int output = 0;

		for (int level = 1; level <= 99; level++) {
			points += Math.floor(level + 300.0 * Math.pow(2.0, level / 7.0));
			output = points / 4;

			if ((output - 1) >= xp)
				return level;
		}

		return 99;
	}

	private final int[] level = new int[24];
	private final double[] exp = new double[level.length];
	private final List<SkillListener> listeners = new ArrayList<>();
    private int combatLevel;

	public SkillSet() {
		for (int i = 0; i < level.length; i++) {
			level[i] = 1;
			exp[i] = 0;
		}

		level[Skill.HITPOINTS] = 10;
		exp[Skill.HITPOINTS] = 1184;
        combatLevel = getCombatLevel();
	}

	public void addListener(SkillListener listener) {
		listeners.add(listener);
	}

	public void removeListener(SkillListener listener) {
		listeners.remove(listener);
	}

	public void removeListeners() {
		listeners.clear();
	}

	public int size() {
		return level.length;
	}

	public int getCurrentLevel(int skill) {
		return level[skill];
	}

	public int getMaximumLevel(int skill) {
		return getLevelFromExperience(exp[skill]);
	}

	public double getExperience(int skill) {
		return exp[skill];
	}

	public void addExperience(int skill, double xp) {
		if (xp < 0)
			throw new IllegalArgumentException("Experience cannot be negative.");

		int oldLevel = getMaximumLevel(skill);
		exp[skill] = Math.min(exp[skill] + xp, MAXIMUM_EXPERIENCE);

		int delta = getMaximumLevel(skill) - oldLevel;
		level[skill] += delta;

		for (SkillListener listener : listeners)
			listener.skillChanged(this, skill);

		if (delta > 0) {
			for (SkillListener listener : listeners)
				listener.skillLevelledUp(this, delta, skill);
		}
	}

	public void refresh() {
		for (int skill = 0; skill < level.length; skill++) {
			for (SkillListener listener : listeners)
				listener.skillChanged(this, skill);
		}
	}

	public int getCombatLevel() {
		int defence   = getMaximumLevel(Skill.DEFENCE);
		int hitpoints = getMaximumLevel(Skill.HITPOINTS);
		int prayer    = getMaximumLevel(Skill.PRAYER);
		int attack    = getMaximumLevel(Skill.ATTACK);
		int strength  = getMaximumLevel(Skill.STRENGTH);
		int magic     = getMaximumLevel(Skill.MAGIC);
		int ranged    = getMaximumLevel(Skill.RANGED);
		int summoning = getMaximumLevel(Skill.SUMMONING); // TODO set to zero on an F2P world

		double base = 1.3 * Math.max(Math.max(attack + strength, 1.5 * magic), 1.5 * ranged);

		return (int) Math.floor((defence + hitpoints + Math.floor(prayer / 2.0) + Math.floor(summoning / 2.0) + base) / 4.0);
	}

	public int getTotalLevel() {
		int total = 0;
		for (int skill = 0; skill < level.length; skill++)
			total += getMaximumLevel(skill);
		return total;
	}

}
