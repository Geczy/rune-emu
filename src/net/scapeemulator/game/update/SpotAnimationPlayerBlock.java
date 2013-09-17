package net.scapeemulator.game.update;

import net.scapeemulator.game.model.SpotAnimation;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.impl.PlayerUpdateMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class SpotAnimationPlayerBlock extends PlayerBlock {

	private final SpotAnimation spotAnimation;

	public SpotAnimationPlayerBlock(Player player) {
		super(0x100);
		this.spotAnimation = player.getSpotAnimation();
	}

	@Override
	public void encode(PlayerUpdateMessage message, GameFrameBuilder builder) {
		builder.put(DataType.SHORT, DataOrder.LITTLE, spotAnimation.getId());
		builder.put(DataType.INT, DataOrder.INVERSED_MIDDLE, (spotAnimation.getHeight() << 16) | spotAnimation.getDelay());
	}

}
