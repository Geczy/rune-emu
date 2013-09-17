package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.msg.Message;

public final class RegionChangeMessage extends Message {

	private final Position position;

	public RegionChangeMessage(Position position) {
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

}
