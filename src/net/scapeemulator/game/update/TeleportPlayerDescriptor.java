package net.scapeemulator.game.update;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.impl.PlayerUpdateMessage;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class TeleportPlayerDescriptor extends PlayerDescriptor {

	private final boolean regionChanging;

	public TeleportPlayerDescriptor(Player player, int[] tickets) {
		super(player, tickets);
		this.regionChanging = player.isRegionChanging();
	}

	@Override
	public void encodeDescriptor(PlayerUpdateMessage message, GameFrameBuilder builder, GameFrameBuilder blockBuilder) {
		Position lastKnownRegion = message.getLastKnownRegion();
		Position position = message.getPosition();

		int x = position.getLocalX(lastKnownRegion.getCentralRegionX());
		int y = position.getLocalY(lastKnownRegion.getCentralRegionY());
		int height = position.getHeight();

		builder.putBits(1, 1);
		builder.putBits(2, 3);
		builder.putBits(7, y);
		builder.putBits(1, regionChanging ? 0 : 1);
		builder.putBits(2, height);
		builder.putBits(1, isBlockUpdatedRequired() ? 1 : 0);
		builder.putBits(7, x);
	}

}
