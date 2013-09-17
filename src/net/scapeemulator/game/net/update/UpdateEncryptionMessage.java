package net.scapeemulator.game.net.update;

public final class UpdateEncryptionMessage {

	private final int key;

	public UpdateEncryptionMessage(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}

}
