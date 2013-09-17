package net.scapeemulator.game.net.world;

public final class WorldListMessage {

	private final int sessionId;
	private final Country[] countries;
	private final World[] worlds;
	private final int[] players;

	public WorldListMessage(int sessionId, Country[] countries, World[] worlds, int[] players) {
		this.sessionId = sessionId;
		this.countries = countries;
		this.worlds = worlds;
		this.players = players;
	}

	public int getSessionId() {
		return sessionId;
	}

	public Country[] getCountries() {
		return countries;
	}

	public World[] getWorlds() {
		return worlds;
	}

	public int[] getPlayers() {
		return players;
	}

}
