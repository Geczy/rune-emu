package net.scapeemulator.game.net.register;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public final class RegisterResponse {

	public static final int STATUS_OK = 2;
	public static final int STATUS_ERROR_CONTACTING_CREATE_SYSTEM = 3;
	public static final int STATUS_SERVER_BUSY = 7;
	public static final int STATUS_CANNOT_CREATE_AT_THIS_TIME = 9;
	public static final int STATUS_DOB_INVALID = 10;
	public static final int STATUS_DOB_FUTURE = 11;
	public static final int STATUS_DOB_THIS_YEAR = 12;
	public static final int STATUS_DOB_LAST_YEAR = 13;
	public static final int STATUS_COUNTRY_INVALID = 14;
	public static final int STATUS_USERNAME_UNAVAILABLE = 20;
	public static final int STATUS_USERNAME_SUGGESTIONS = 21;
	/* payload: 1 byte count of usernames, n * 8 byte base37 usernames */
	public static final int STATUS_USERNAME_INVALID = 22;
	public static final int STATUS_PASSWORD_INVALID_LENGTH = 30;
	public static final int STATUS_PASSWORD_INVALID_CHARS = 31;
	public static final int STATUS_PASSWORD_TOO_EASY = 32;
	/* 33 = same as above */
	public static final int STATUS_PASSWORD_TOO_SIMILAR_TO_USERNAME = 34;
	/* 35, 36 = same as above */
	public static final int STATUS_SERVER_UPDATED = 37;
	/* 38 = cannot create an account at this time */

	private final int status;
	private final ByteBuf payload;

	public RegisterResponse(int status) {
		this.status = status;
		this.payload = Unpooled.EMPTY_BUFFER;
	}

	public RegisterResponse(int status, ByteBuf payload) {
		this.status = status;
		this.payload = payload;
	}

	public int getStatus() {
		return status;
	}

	public ByteBuf getPayload() {
		return payload;
	}

}
