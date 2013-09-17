package net.scapeemulator.game.model.player.action;

import net.scapeemulator.game.model.SpotAnimation;
import net.scapeemulator.game.model.mob.Animation;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.task.Action;

public final class EmoteAction extends Action<Player> {

	public static final int YES = 855;
	public static final int NO = 856;
	public static final int BOW = 858;
	public static final int ANGRY = 859;
	public static final int THINK = 857;
	public static final int WAVE = 863;
	public static final int SHRUG = 2113;
	public static final int CHEER = 862;
	public static final int BECKON = 864;
	public static final int JUMP_FOR_JOY = 2109;
	public static final int LAUGH = 861;
	public static final int YAWN = 2111;
	public static final int DANCE = 866;
	public static final int JIG = 2106;
	public static final int SPIN = 2107;
	public static final int HEADBANG = 2108;
	public static final int CRY = 860;
	public static final int BLOW_KISS = 1368;
	public static final int BLOW_KISS_GRAPHIC = 574;
	public static final int PANIC = 2105;
	public static final int RASPBERRY = 2110;
	public static final int CLAP = 865;
	public static final int SALUTE = 2112;

	public EmoteAction(Player player, int animation, int delay) {
		this(player, animation, -1, delay);
	}

	public EmoteAction(Player player, int animation, int spotAnimation, int delay) {
		super(player, delay, false);

		player.getWalkingQueue().reset();
		player.playAnimation(new Animation(animation));
		if (spotAnimation != -1)
			player.playSpotAnimation(new SpotAnimation(spotAnimation));
	}

	public void execute() {
		stop();
	}

}
