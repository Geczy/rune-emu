package net.scapeemulator.flooder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import net.scapeemulator.cache.Cache;
import net.scapeemulator.cache.ChecksumTable;
import net.scapeemulator.cache.FileStore;
import net.scapeemulator.util.NetworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public final class Flooder {

	private static final Logger logger = LoggerFactory.getLogger(Flooder.class);

	public static void main(String[] args) {
		InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
		try {
			Flooder flooder = new Flooder();
			flooder.start();
		} catch (Throwable t) {
			logger.error("Error starting flooder:", t);
		}
	}

	private final Cache cache;
	private final Bootstrap bootstrap = new Bootstrap();

	public Flooder() throws IOException {
		cache = new Cache(FileStore.open("../game/data/cache/"));
		ChecksumTable table = cache.createChecksumTable();
		int[] crc = new int[28];
		for (int i = 0; i < crc.length; i++)
			crc[i] = table.getEntry(i).getCrc();

		bootstrap.remoteAddress(new InetSocketAddress(InetAddress.getLoopbackAddress(), NetworkConstants.GAME_PORT));
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.group(new NioEventLoopGroup());
		bootstrap.handler(new FlooderChannelInitializer(crc));
	}

	public void start() throws InterruptedException {
		for (int i = 0; i < 2000; i++)
			bootstrap.connect();
	}

}
