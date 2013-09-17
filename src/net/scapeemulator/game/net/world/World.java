package net.scapeemulator.game.net.world;

public final class World {

	public static final int FLAG_MEMBERS    = 0x1;
	public static final int FLAG_QUICK_CHAT = 0x2;
	public static final int FLAG_PVP        = 0x4;
	public static final int FLAG_LOOT_SHARE = 0x8;
	public static final int FLAG_HIGHLIGHT  = 0x10;

	private final int id, flags, country;
	private final String activity, ip;

	public World(int id, int flags, int country, String activity, String ip) {
		this.id = id;
		this.flags = flags;
		this.country = country;
		this.activity = activity;
		this.ip = ip;
	}

	public int getId() {
		return id;
	}

	public int getFlags() {
		return flags;
	}

	public int getCountry() {
		return country;
	}

	public String getActivity() {
		return activity;
	}

	public String getIp() {
		return ip;
	}

}
