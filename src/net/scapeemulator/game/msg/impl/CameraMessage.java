package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class CameraMessage extends Message {

	private final int yaw, pitch;

	public CameraMessage(int yaw, int pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public int getYaw() {
		return yaw;
	}

	public int getPitch() {
		return pitch;
	}

}
