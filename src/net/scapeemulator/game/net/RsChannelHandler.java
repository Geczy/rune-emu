package net.scapeemulator.game.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import net.scapeemulator.game.GameServer;
import net.scapeemulator.game.net.auto.AutoLoginSession;
import net.scapeemulator.game.net.handshake.HandshakeMessage;
import net.scapeemulator.game.net.jaggrab.JaggrabSession;
import net.scapeemulator.game.net.login.LoginSession;
import net.scapeemulator.game.net.register.RegisterSession;
import net.scapeemulator.game.net.update.UpdateSession;
import net.scapeemulator.game.net.world.WorldListSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class RsChannelHandler extends ChannelInboundMessageHandlerAdapter<Object> { /* TODO: more specific generics here? */

	private static final Logger logger = LoggerFactory.getLogger(RsChannelHandler.class);

	private final GameServer server;
	private Session session;

	public RsChannelHandler(GameServer server) {
		this.server = server;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		logger.info("Channel connected: " + ctx.channel().remoteAddress() + ".");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		if (session != null)
			session.channelClosed();

		logger.info("Channel disconnected: " + ctx.channel().remoteAddress() + ".");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.warn("Exception caught, closing channel...", cause);
		ctx.close();
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, Object message) throws IOException {
		if (session != null) {
			session.messageReceived(message);
		} else {
			HandshakeMessage handshake = (HandshakeMessage) message;

			switch (handshake.getService()) {
			case HandshakeMessage.SERVICE_LOGIN:
				session = new LoginSession(server, ctx.channel());
				break;

			case HandshakeMessage.SERVICE_UPDATE:
				session = new UpdateSession(server, ctx.channel());
				break;

			case HandshakeMessage.SERVICE_JAGGRAB:
				session = new JaggrabSession(server, ctx.channel());
				break;

			case HandshakeMessage.SERVICE_REGISTER_PERSONAL_DETAILS:
			case HandshakeMessage.SERVICE_REGISTER_USERNAME:
			case HandshakeMessage.SERVICE_REGISTER_COMMIT:
				session = new RegisterSession(server, ctx.channel());
				break;

			case HandshakeMessage.SERVICE_AUTO_LOGIN:
				session = new AutoLoginSession(server, ctx.channel());
				break;

			case HandshakeMessage.SERVICE_WORLD_LIST:
				session = new WorldListSession(server, ctx.channel());
				break;
			}
		}
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
