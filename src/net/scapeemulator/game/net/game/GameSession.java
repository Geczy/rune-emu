package net.scapeemulator.game.net.game;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import net.scapeemulator.game.GameServer;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.player.PlayerOption;
import net.scapeemulator.game.msg.Message;
import net.scapeemulator.game.msg.MessageDispatcher;
import net.scapeemulator.game.msg.impl.RegionChangeMessage;
import net.scapeemulator.game.net.Session;

public final class GameSession extends Session {

	private final Player player;
	private final MessageDispatcher dispatcher;
	private final Queue<Message> messages = new ArrayDeque<>();

	public GameSession(GameServer server, Channel channel, Player player) {
		super(server, channel);
		this.player = player;
		this.dispatcher = server.getMessageDispatcher();
	}

	public void init() {
		player.setSession(this);

		/* set up player for their initial region change */
		Position position = player.getPosition();
		player.setLastKnownRegion(position);
		player.send(new RegionChangeMessage(player.getPosition()));

		/* set up the game interface */
		player.getInterfaceSet().init();
		player.sendMessage("Welcome to Evelus.");
                
                /* Set all the default options that all players have available */
                player.getOption(PlayerOption.FOLLOW_OPTION).setText("Follow");
                player.getOption(PlayerOption.TRADE_OPTION).setText("Trade with");
                player.refreshOptions();

		/* fireEvents skills, inventory, energy, etc. */
		player.getInventory().refresh();
		player.getEquipment().refresh();
		player.getSettings().refresh();
		player.getSkillSet().refresh();               
		player.setEnergy(player.getEnergy()); // TODO: nicer way than this?
	}

	@Override
	public void messageReceived(Object message) throws IOException {
		synchronized (messages) {
			messages.add((Message) message);
		}
	}

	@Override
	public void channelClosed() {
		server.getLoginService().addLogoutRequest(player);
	}

	public ChannelFuture send(Message message) {
		return channel.write(message);
	}

	public void processMessageQueue() {
		synchronized (messages) {
			Message message;
			while ((message = messages.poll()) != null)
				dispatcher.dispatch(player, message);
		}
	}

}
