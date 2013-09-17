package net.scapeemulator.game.net.register;

import java.util.Calendar;

public final class RegisterPersonalDetailsRequest {

	private final Calendar dateOfBirth;
	private final int country;

	public RegisterPersonalDetailsRequest(Calendar dateOfBirth, int country) {
		this.dateOfBirth = dateOfBirth;
		this.country = country;
	}

	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}

	public int getCountry() {
		return country;
	}

}
