package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class EnergyMessage extends Message {

	private final int energy;

	public EnergyMessage(int energy) {
		this.energy = energy;
	}

	public int getEnergy() {
		return energy;
	}

}
