package net.scapeemulator.game.net.register;

import java.util.Calendar;

public final class RegisterCommitRequest {

	private final int version;
	private final String username, password;
	private final Calendar dateOfBirth;
	private final int country;

	public RegisterCommitRequest(int version, String username, String password, Calendar dateOfBirth, int country) {
		this.version = version;
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.country = country;
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

	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}

	public int getCountry() {
		return country;
	}

}
