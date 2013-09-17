package net.scapeemulator.game.net.update;

public final class FileRequest {

	private final boolean priority;
	private final int type, file;

	public FileRequest(boolean priority, int type, int file) {
		this.priority = priority;
		this.type = type;
		this.file = file;
	}

	public boolean isPriority() {
		return priority;
	}

	public int getType() {
		return type;
	}

	public int getFile() {
		return file;
	}

}
