package net.scapeemulator.game.msg.impl;

import java.util.List;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.msg.Message;
import net.scapeemulator.game.update.PlayerDescriptor;

public final class PlayerUpdateMessage extends Message {

	private final Position lastKnownRegion;
	private final Position position;
	private final int localPlayerCount;
	private final PlayerDescriptor selfDescriptor;
	private final List<PlayerDescriptor> descriptors;

	public PlayerUpdateMessage(Position lastKnownRegion, Position position, int localPlayerCount, PlayerDescriptor selfDescriptor, List<PlayerDescriptor> descriptors) {
		this.lastKnownRegion = lastKnownRegion;
		this.position = position;
		this.localPlayerCount = localPlayerCount;
		this.selfDescriptor = selfDescriptor;
		this.descriptors = descriptors;
	}

	public Position getLastKnownRegion() {
		return lastKnownRegion;
	}

	public Position getPosition() {
		return position;
	}

	public int getLocalPlayerCount() {
		return localPlayerCount;
	}

	public PlayerDescriptor getSelfDescriptor() {
		return selfDescriptor;
	}

	public List<PlayerDescriptor> getDescriptors() {
		return descriptors;
	}

}
