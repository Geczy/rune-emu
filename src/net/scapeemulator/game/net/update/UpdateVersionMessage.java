package net.scapeemulator.game.net.update;

public final class UpdateVersionMessage {

	private final int version;

	public UpdateVersionMessage(int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

}
