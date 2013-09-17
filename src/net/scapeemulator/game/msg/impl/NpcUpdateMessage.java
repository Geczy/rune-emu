package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.msg.Message;
import net.scapeemulator.game.update.NpcDescriptor;

import java.util.List;

public final class NpcUpdateMessage extends Message {

	private final Position lastKnownRegion;
	private final Position position;
	private final int localNpcCount;
	private final List<NpcDescriptor> descriptors;

	public NpcUpdateMessage(Position lastKnownRegion, Position position, int localNpcCount, List<NpcDescriptor> descriptors) {
		this.lastKnownRegion = lastKnownRegion;
		this.position = position;
		this.localNpcCount = localNpcCount;
		this.descriptors = descriptors;
	}

	public Position getLastKnownRegion() {
		return lastKnownRegion;
	}

	public Position getPosition() {
		return position;
	}

	public int getLocalNpcCount() {
		return localNpcCount;
	}

	public List<NpcDescriptor> getDescriptors() {
		return descriptors;
	}

}
