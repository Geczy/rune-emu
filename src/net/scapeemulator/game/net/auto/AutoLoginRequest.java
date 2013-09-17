package net.scapeemulator.game.net.auto;

public final class AutoLoginRequest {

	private final int version;
	private final String username, password;

	public AutoLoginRequest(int version, String username, String password) {
		this.version = version;
		this.username = username;
		this.password = password;
	}

	public int getVersion() {
		return version;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
