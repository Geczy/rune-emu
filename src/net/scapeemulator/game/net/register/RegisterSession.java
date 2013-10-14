package net.scapeemulator.game.net.register;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import java.io.IOException;

import net.scapeemulator.game.GameServer;
import net.scapeemulator.game.net.Session;

public final class RegisterSession extends Session {

	public RegisterSession(GameServer server, Channel channel) {
		super(server, channel);
	}

	@Override
	public void messageReceived(Object message) throws IOException {
		channel.write(new RegisterResponse(RegisterResponse.STATUS_OK)).addListener(ChannelFutureListener.CLOSE);
	}

}
