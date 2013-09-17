package net.scapeemulator.game.net.login;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public final class LoginResponse {

	public static final int STATUS_EXCHANGE_KEYS = 0;
	public static final int STATUS_ADVERTISEMENT = 1;
	public static final int STATUS_OK = 2;
	public static final int STATUS_INVALID_PASSWORD = 3;
	public static final int STATUS_BANNED = 4;
	public static final int STATUS_ALREADY_ONLINE = 5;
	public static final int STATUS_GAME_UPDATED = 6;
	public static final int STATUS_WORLD_FULL = 7;
	public static final int STATUS_LOGIN_SERVER_OFFLINE = 8;
	public static final int STTAUS_LOGIN_LIMIT_EXCEEDED = 9;
	public static final int STATUS_BAD_SESSION_ID = 10;
	public static final int STATUS_FORCE_CHANGE_PASSWORD = 11;
	public static final int STATUS_WORLD_MEMBERS = 12;
	public static final int STATUS_COULD_NOT_COMPLETE = 13;
	public static final int STATUS_UPDATE_IN_PROGRESS = 14;
	public static final int STATUS_TOO_MANY_FAILED_LOGINS = 16;
	public static final int STATUS_IN_MEMBERS_ONLY_AREA = 17;
	public static final int STATUS_ACCOUNT_LOCKED = 18;
	public static final int STATUS_FULLSCREEN_MEMBERS = 19;
	public static final int STATUS_LOGIN_SERVER_INVALID = 20;
	public static final int STATUS_RETRY = 21;
	public static final int STATUS_MALFORMED_PACKET = 22;
	public static final int STATUS_LOGIN_SERVER_NO_REPLY = 23;
	public static final int STATUS_ERROR_LOADING_PROFILE = 24;
	public static final int STATUS_LOGIN_SERVER_INVALID_RESPONSE = 25;
	public static final int STATUS_IP_BANNED = 26;
	public static final int STATUS_SERVICE_UNAVAILABLE = 27;
	public static final int STATUS_CLIENT_MEMBERS = 30;
	public static final int STATUS_SWITCH_WORLD_AND_RETRY = 101;

	private final int status;
	private final ByteBuf payload;

	public LoginResponse(int status) {
		this.status = status;
		this.payload = Unpooled.EMPTY_BUFFER;
	}

	public LoginResponse(int status, ByteBuf payload) {
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
