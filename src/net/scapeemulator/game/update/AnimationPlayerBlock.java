package net.scapeemulator.game.update;

import net.scapeemulator.game.model.mob.Animation;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.impl.PlayerUpdateMessage;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class AnimationPlayerBlock extends PlayerBlock {

	private final Animation animation;

	public AnimationPlayerBlock(Player player) {
		super(0x8);
		this.animation = player.getAnimation();
	}

	@Override
	public void encode(PlayerUpdateMessage message, GameFrameBuilder builder) {
		builder.put(DataType.SHORT, animation.getId());
		builder.put(DataType.BYTE, animation.getDelay());
	}

}
