package net.scapeemulator.game.net.login;

public final class LoginRequest {

	private final boolean reconnecting;
	private final String username, password;
	private final long clientSessionKey, serverSessionKey;
	private final int version;
	private final int[] crc;
	private final int displayMode;

	public LoginRequest(boolean reconnecting, String username, String password, long clientSessionKey, long serverSessionKey, int version, int[] crc, int displayMode) {
		this.reconnecting = reconnecting;
		this.username = username;
		this.password = password;
		this.clientSessionKey = clientSessionKey;
		this.serverSessionKey = serverSessionKey;
		this.version = version;
		this.crc = crc;
		this.displayMode = displayMode;
	}

	public boolean isReconnecting() {
		return reconnecting;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public long getClientSessionKey() {
		return clientSessionKey;
	}

	public long getServerSessionKey() {
		return serverSessionKey;
	}

	public int getVersion() {
		return version;
	}

	public int[] getCrc() {
		return crc;
	}

	public int getDisplayMode() {
		return displayMode;
	}

}
