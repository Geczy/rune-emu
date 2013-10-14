package net.scapeemulator.game.io;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.scapeemulator.game.io.jdbc.ItemsTable;
import net.scapeemulator.game.io.jdbc.PlayersTable;
import net.scapeemulator.game.io.jdbc.SettingsTable;
import net.scapeemulator.game.io.jdbc.Table;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.player.inventory.Inventory;
import net.scapeemulator.game.net.login.LoginResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JdbcPlayerSerializer extends PlayerSerializer implements Closeable {

	private static final Logger logger = LoggerFactory.getLogger(JdbcPlayerSerializer.class);

	private final Connection connection;
	private final PreparedStatement loginStatement;
	private final Table[] tables;

	public JdbcPlayerSerializer(String url, String username, String password) throws SQLException {
		connection = DriverManager.getConnection(url, username, password);
		connection.setAutoCommit(false);
		loginStatement = connection.prepareStatement("SELECT id, password FROM players WHERE username = ?;");
		tables = new Table[] {
			new PlayersTable(connection),
			new SettingsTable(connection),
			new ItemsTable(connection, "inventory") {
				@Override
				public Inventory getInventory(Player player) {
					return player.getInventory();
				}
			},
			new ItemsTable(connection, "equipment") {
				@Override
				public Inventory getInventory(Player player) {
					return player.getEquipment();
				}
			},
		};
	}

	@Override
	public SerializeResult load(String username, String password) {
		try {
			loginStatement.setString(1, username);
			try (ResultSet set = loginStatement.executeQuery()) {
				if (set.first()) {
					int id = set.getInt("id");
					String hashedPassword = set.getString("password");

					if (BCrypt.checkpw(password, hashedPassword)) {
						Player player = new Player();
						player.setDatabaseId(id);
						player.setPassword(password); /* can't use hashed one in PlayerTable */

						for (Table table : tables)
							table.load(player);

						return new SerializeResult(LoginResponse.STATUS_OK, player);
					}
				}

				return new SerializeResult(LoginResponse.STATUS_INVALID_PASSWORD);
			}
		} catch (SQLException | IOException ex) {
			logger.warn("Loading player " + username + " failed.", ex);
			return new SerializeResult(LoginResponse.STATUS_COULD_NOT_COMPLETE);
		}
	}

	@Override
	public void save(Player player) {
		try {
			for (Table table : tables)
				table.save(player);

			connection.commit();
		} catch (SQLException | IOException ex) {
			try {
				connection.rollback();
			} catch (SQLException innerEx) {
				/* ignore rollback failure, not much we can do */
			}

			logger.warn("Saving player " + player.getUsername() + " failed.", ex);
		}
	}

	@Override
	public void close() throws IOException {
		try {
			connection.close();
		} catch (SQLException ex) {
			throw new IOException(ex);
		}
	}

}
