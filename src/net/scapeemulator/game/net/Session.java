package net.scapeemulator.game.net;

import io.netty.channel.Channel;
import net.scapeemulator.game.GameServer;

import java.io.IOException;

public abstract class Session {

	protected final GameServer server;
	protected final Channel channel;

	public Session(GameServer server, Channel channel) {
		this.server = server;
		this.channel = channel;
	}

	public abstract void messageReceived(Object message) throws IOException;

	public void channelClosed() {
		/* empty */
	}

}
