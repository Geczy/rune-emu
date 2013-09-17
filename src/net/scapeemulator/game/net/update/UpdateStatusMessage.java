package net.scapeemulator.game.net.update;

public final class UpdateStatusMessage {

	public static final int STATUS_OK = 0;
	public static final int STATUS_OUT_OF_DATE = 6;
	public static final int STATUS_SERVER_FULL = 7;

	private final int status;

	public UpdateStatusMessage(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
