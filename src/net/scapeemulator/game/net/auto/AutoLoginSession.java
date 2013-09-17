package net.scapeemulator.game.net.auto;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import net.scapeemulator.game.GameServer;
import net.scapeemulator.game.net.Session;
import net.scapeemulator.game.net.login.LoginResponse;

import java.io.IOException;

public final class AutoLoginSession extends Session {

	public AutoLoginSession(GameServer server, Channel channel) {
		super(server, channel);
	}

	@Override
	public void messageReceived(Object message) throws IOException {
		ByteBuf payload = channel.alloc().buffer(2);
		payload.writeShort(1);
		LoginResponse response = new LoginResponse(LoginResponse.STATUS_SWITCH_WORLD_AND_RETRY, payload);
		channel.write(response).addListener(ChannelFutureListener.CLOSE);
	}

}
