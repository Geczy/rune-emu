package net.scapeemulator.game.net.update;

import io.netty.buffer.ByteBuf;

public final class FileResponse {

	private final boolean priority;
	private final int type, file;
	private final ByteBuf container;

	public FileResponse(boolean priority, int type, int file, ByteBuf container) {
		this.priority = priority;
		this.type = type;
		this.file = file;
		this.container = container;
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

	public ByteBuf getContainer() {
		return container;
	}

}
