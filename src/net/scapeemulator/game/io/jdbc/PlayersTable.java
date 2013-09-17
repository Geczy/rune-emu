package net.scapeemulator.game.io.jdbc;

import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.Position;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class PlayersTable extends Table {

	private final PreparedStatement loadStatement;
	private final PreparedStatement saveStatement;

	public PlayersTable(Connection connection) throws SQLException {
		this.loadStatement = connection.prepareStatement("SELECT * FROM players WHERE id = ?");
		this.saveStatement = connection.prepareStatement("REPLACE INTO players (id, username, password, rights, x, y, height) VALUES (?, ?, ?, ?, ?, ?, ?);");
	}

	@Override
	public void load(Player player) throws SQLException, IOException {
		loadStatement.setInt(1, player.getDatabaseId());

		try (ResultSet set = loadStatement.executeQuery()) {
			if (!set.first())
				throw new IOException();

			player.setUsername(set.getString("username"));
			player.setRights(set.getInt("rights"));

			int x = set.getInt("x");
			int y = set.getInt("y");
			int height = set.getInt("height");
			player.setPosition(new Position(x, y, height));
		}
	}

	@Override
	public void save(Player player) throws SQLException {
		saveStatement.setInt(1, player.getDatabaseId());
		saveStatement.setString(2, player.getUsername());
		saveStatement.setString(3, BCrypt.hashpw(player.getPassword(), BCrypt.gensalt()));
		saveStatement.setInt(4, player.getRights());

		Position position = player.getPosition();
		saveStatement.setInt(5, position.getX());
		saveStatement.setInt(6, position.getY());
		saveStatement.setInt(7, position.getHeight());

		saveStatement.execute();
	}

}
