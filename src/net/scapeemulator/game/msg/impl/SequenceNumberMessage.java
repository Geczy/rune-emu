package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class SequenceNumberMessage extends Message {

	private final int sequenceNumber;

	public SequenceNumberMessage(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

}
