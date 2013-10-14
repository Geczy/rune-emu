package net.scapeemulator.game.net.update;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import net.scapeemulator.cache.Cache;
import net.scapeemulator.cache.ChecksumTable;
import net.scapeemulator.cache.Container;
import net.scapeemulator.game.GameServer;
import net.scapeemulator.game.net.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UpdateSession extends Session {

	private static final Logger logger = LoggerFactory.getLogger(UpdateSession.class);

	private final UpdateService service;
	private final Deque<FileRequest> fileQueue = new ArrayDeque<>();
	private boolean idle = true;
	private boolean handshakeComplete = false;

	public UpdateSession(GameServer server, Channel channel) {
		super(server, channel);
		this.service = server.getUpdateService();
	}

	public void processFileQueue() {
		FileRequest request;

		synchronized (fileQueue) {
			request = fileQueue.pop();
			if (fileQueue.isEmpty()) {
				idle = true;
			} else {
				service.addPendingSession(this);
				idle = false;
			}
		}

		if (request != null) {
			int type = request.getType();
			int file = request.getFile();

			Cache cache = server.getCache();
			ByteBuf buf;

			try {
				if (type == 255 && file == 255) {
					ChecksumTable table = server.getChecksumTable();
					Container container = new Container(Container.COMPRESSION_NONE, table.encode());
					buf = Unpooled.wrappedBuffer(container.encode());
				} else {
					buf = Unpooled.wrappedBuffer(cache.getStore().read(type, file));
					if (type != 255)
						buf = buf.slice(0, buf.readableBytes() - 2);
				}
				channel.write(new FileResponse(request.isPriority(), type, file, buf));
			} catch (IOException ex) {
				logger.warn("Failed to service file request " + type + ", " + file + ".", ex);
			}
		}
	}

	@Override
	public void messageReceived(Object message) {
		if (handshakeComplete) {
			if (message instanceof FileRequest) {
				FileRequest request = (FileRequest) message;

				synchronized (fileQueue) {
					if (request.isPriority()) {
						fileQueue.addFirst(request);
					} else {
						fileQueue.addLast(request);
					}

					if (idle) {
						service.addPendingSession(this);
						idle = false;
					}
				}
			} else if (message instanceof UpdateEncryptionMessage) {
				UpdateEncryptionMessage encryption = (UpdateEncryptionMessage) message;
				XorEncoder encoder = channel.pipeline().get(XorEncoder.class);
				encoder.setKey(encryption.getKey());
			}
		} else {
			UpdateVersionMessage version = (UpdateVersionMessage) message;

			int status;
			if (version.getVersion() == server.getVersion()) {
				status = UpdateStatusMessage.STATUS_OK;
			} else {
				status = UpdateStatusMessage.STATUS_OUT_OF_DATE;
			}

			ChannelFuture future = channel.write(new UpdateStatusMessage(status));
			if (status == UpdateStatusMessage.STATUS_OK) {
				/* the client won't re-connect so an ondemand session cannot time out */
				channel.pipeline().remove(ReadTimeoutHandler.class);
				handshakeComplete = true;
			} else {
				future.addListener(ChannelFutureListener.CLOSE);
			}
		}
	}

}
