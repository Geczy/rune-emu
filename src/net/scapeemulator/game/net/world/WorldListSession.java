package net.scapeemulator.game.net.world;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import net.scapeemulator.game.GameServer;
import net.scapeemulator.game.net.Session;

public final class WorldListSession extends Session {

	private static final Country[] COUNTRIES = {
		new Country(Country.FLAG_USA, "USA")
	};

	public WorldListSession(GameServer server, Channel channel) {
		super(server, channel);
	}

	@Override
	public void messageReceived(Object message) {
		World[] worlds = { new World(1, World.FLAG_MEMBERS | World.FLAG_HIGHLIGHT, 0, "Non-Local", "127.0.0.1"), new World(2, World.FLAG_MEMBERS | World.FLAG_HIGHLIGHT, 0, "Local", "127.0.0.1"), new World(3, World.FLAG_MEMBERS | World.FLAG_HIGHLIGHT, 0, "LAN", "192.168.2.10") };
		int[] players = { 0, 0, 0 };
		channel.write(new WorldListMessage(0xDEADBEEF, COUNTRIES, worlds, players)).addListener(ChannelFutureListener.CLOSE);
	}

}
