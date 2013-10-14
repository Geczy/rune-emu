package net.scapeemulator.game.io;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.net.login.LoginResponse;

public final class DummyPlayerSerializer extends PlayerSerializer {

	@Override
	public SerializeResult load(String username, String password) {
		Player player = new Player();
		player.setUsername(username);
		player.setPassword(password);
		player.setRights(2);
		player.setPosition(new Position(3222, 3222));
		return new SerializeResult(LoginResponse.STATUS_OK, player);
	}

	@Override
	public void save(Player player) {
		/* discard player */
	}

}
