package net.scapeemulator.game.msg.impl;

import net.scapeemulator.game.msg.Message;

public final class FocusMessage extends Message {

	private final boolean focused;

	public FocusMessage(boolean focused) {
		this.focused = focused;
	}

	public boolean isFocused() {
		return focused;
	}

}
