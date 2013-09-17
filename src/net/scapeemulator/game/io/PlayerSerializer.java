package net.scapeemulator.game.io;

import net.scapeemulator.game.model.player.Player;

public abstract class PlayerSerializer {

	public static class SerializeResult {

		private final int status;
		private final Player player;

		public SerializeResult(int status) {
			this(status, null);
		}

		public SerializeResult(int status, Player player) {
			this.status = status;
			this.player = player;
		}

		public int getStatus() {
			return status;
		}

		public Player getPlayer() {
			return player;
		}

	}

	public abstract SerializeResult load(String username, String password);
	public abstract void save(Player player);

}
