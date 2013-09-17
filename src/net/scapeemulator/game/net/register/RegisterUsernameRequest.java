package net.scapeemulator.game.net.register;

public final class RegisterUsernameRequest {

	private final String username;

	public RegisterUsernameRequest(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

}
