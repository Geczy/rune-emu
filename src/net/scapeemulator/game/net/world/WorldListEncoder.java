package net.scapeemulator.game.net.world;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.scapeemulator.util.ByteBufUtils;

public final class WorldListEncoder extends MessageToByteEncoder<WorldListMessage> {

	@Override
	public void encode(ChannelHandlerContext ctx, WorldListMessage list, ByteBuf out) {
		ByteBuf buf = ctx.alloc().buffer();
		buf.writeByte(1);
		buf.writeByte(1);

		Country[] countries = list.getCountries();
		ByteBufUtils.writeSmart(buf, countries.length);
		for (Country country : countries) {
			ByteBufUtils.writeSmart(buf, country.getFlag());
			ByteBufUtils.writeWorldListString(buf, country.getName());
		}

		World[] worlds = list.getWorlds();
		int minId = worlds[0].getId();
		int maxId = worlds[0].getId();
		for (int i = 1; i < worlds.length; i++) {
			World world = worlds[i];
			int id = world.getId();

			if (id > maxId)
				maxId = id;
			if (id < minId)
				minId = id;
		}

		ByteBufUtils.writeSmart(buf, minId);
		ByteBufUtils.writeSmart(buf, maxId);
		ByteBufUtils.writeSmart(buf, worlds.length);

		for (World world : worlds) {
			ByteBufUtils.writeSmart(buf, world.getId() - minId);
			buf.writeByte(world.getCountry());
			buf.writeInt(world.getFlags());
			ByteBufUtils.writeWorldListString(buf, world.getActivity());
			ByteBufUtils.writeWorldListString(buf, world.getIp());
		}

		buf.writeInt(list.getSessionId());

		int[] players = list.getPlayers();
		for (int i = 0; i < worlds.length; i++) {
			World world = worlds[i];
			ByteBufUtils.writeSmart(buf, world.getId() - minId);
			buf.writeShort(players[i]);
		}

		out.writeByte(0); // 0 = ok, 7/9 = world full
		out.writeShort(buf.readableBytes());
		out.writeBytes(buf);
	}

}
