package net.scapeemulator.game.net.game;

public enum DataType {
	BYTE(1), SHORT(2), TRI_BYTE(3), INT(4), LONG(8);

	private final int bytes;

	private DataType(int bytes) {
		this.bytes = bytes;
	}

	public int getBytes() {
		return bytes;
	}

}
