package net.scapeemulator.game.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.scapeemulator.game.GameServer;
import net.scapeemulator.game.net.handshake.HandshakeDecoder;

public final class RsChannelInitializer extends ChannelInitializer<SocketChannel> {

	private final GameServer server;

	public RsChannelInitializer(GameServer server) {
		this.server = server;
	}

	@Override
	public void initChannel(SocketChannel ch) {
		ch.pipeline().addLast(
			new ReadTimeoutHandler(30),
			new HandshakeDecoder(),
			new RsChannelHandler(server));
	}

}
