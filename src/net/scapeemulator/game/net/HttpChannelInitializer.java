package net.scapeemulator.game.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

public final class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(
			new ReadTimeoutHandler(5),
			new HttpRequestDecoder(),
			new HttpResponseEncoder(),
			new HttpObjectAggregator(1024),
			new HttpChannelHandler());
	}

}
