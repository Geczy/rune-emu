package net.scapeemulator.game.io.jdbc;

import net.scapeemulator.game.model.player.Item;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.player.inventory.Inventory;

import java.io.IOException;
import java.sql.*;

public abstract class ItemsTable extends Table {

	private final PreparedStatement loadStatement;
	private final PreparedStatement saveStatement;
	private final String type;

	public ItemsTable(Connection connection, String type) throws SQLException {
		this.loadStatement = connection.prepareStatement("SELECT * FROM items WHERE player_id = ? AND type = ?;");
		this.saveStatement = connection.prepareStatement("INSERT INTO items (player_id, type, slot, item, amount) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE item = VALUES(item), amount = VALUES(amount);");
		this.type = type;
	}

	public abstract Inventory getInventory(Player player);

	@Override
	public void load(Player player) throws SQLException, IOException {
		loadStatement.setInt(1, player.getDatabaseId());
		loadStatement.setString(2, type);

		Inventory inventory = getInventory(player);
		try (ResultSet set = loadStatement.executeQuery()) {
			while (set.next()) {
				int slot = set.getInt("slot");
				int item = set.getInt("item");
				int amount = set.getInt("amount");

				if (set.wasNull()) {
					inventory.set(slot, null);
				} else {
					inventory.set(slot, new Item(item, amount));
				}
			}
		}
	}

	@Override
	public void save(Player player) throws SQLException, IOException {
		saveStatement.setInt(1, player.getDatabaseId());
		saveStatement.setString(2, type);

		Inventory inventory = getInventory(player);
		Item[] items = inventory.toArray();
		for (int slot = 0; slot < items.length; slot++) {
			Item item = items[slot];

			saveStatement.setInt(3, slot);
			if (item == null) {
				saveStatement.setNull(4, Types.INTEGER);
				saveStatement.setNull(5, Types.INTEGER);
			} else {
				saveStatement.setInt(4, item.getId());
				saveStatement.setInt(5, item.getAmount());
			}

			saveStatement.addBatch();
		}

		saveStatement.executeBatch();
	}

}
