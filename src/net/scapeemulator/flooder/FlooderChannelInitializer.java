package net.scapeemulator.flooder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import net.scapeemulator.flooder.net.FlooderChannelHandler;

public final class FlooderChannelInitializer extends ChannelInitializer<SocketChannel> {

	private final int[] crc;

	public FlooderChannelInitializer(int[] crc) {
		this.crc = crc;
	}

	@Override
	public void initChannel(SocketChannel ch) {
		ch.pipeline().addLast(new FlooderChannelHandler(crc));
	}

}
