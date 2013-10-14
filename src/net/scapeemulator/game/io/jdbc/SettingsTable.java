package net.scapeemulator.game.io.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.player.PlayerSettings;

public final class SettingsTable extends Table {

	private final PreparedStatement loadStatement;
	private final PreparedStatement saveStatement;

	public SettingsTable(Connection connection) throws SQLException {
		this.loadStatement = connection.prepareStatement("SELECT * FROM settings WHERE player_id = ?;");
		this.saveStatement = connection.prepareStatement("INSERT INTO settings (player_id, setting, value) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE value = VALUES(value);");
	}

	@Override
	public void load(Player player) throws SQLException, IOException {
		loadStatement.setInt(1, player.getDatabaseId());

		PlayerSettings settings = player.getSettings();
		try (ResultSet set = loadStatement.executeQuery()) {
			while (set.next()) {
				String setting = set.getString("setting");
				int value = set.getInt("value");

				switch (setting) {
				case "attack_style":
					settings.setAttackStyle(value);
					break;

				case "auto_retaliating":
					settings.setAutoRetaliating(value != 0);
					break;

				case "two_button_mouse":
					settings.setTwoButtonMouse(value != 0);
					break;

				case "chat_fancy":
					settings.setChatFancy(value != 0);
					break;

				case "private_chat_split":
					settings.setPrivateChatSplit(value != 0);
					break;

				case "accepting_aid":
					settings.setAcceptingAid(value != 0);
					break;

				default:
					throw new IOException("unknown setting: " + setting);
				}
			}
		}
	}

	@Override
	public void save(Player player) throws SQLException, IOException {
		saveStatement.setInt(1, player.getDatabaseId());

		PlayerSettings settings = player.getSettings();
		saveStatement.setString(2, "attack_style");
		saveStatement.setInt(3, settings.getAttackStyle());
		saveStatement.addBatch();

		saveStatement.setString(2, "auto_retaliating");
		saveStatement.setInt(3, settings.isAutoRetaliating() ? 1 : 0);
		saveStatement.addBatch();

		saveStatement.setString(2, "two_button_mouse");
		saveStatement.setInt(3, settings.isTwoButtonMouse() ? 1 : 0);
		saveStatement.addBatch();

		saveStatement.setString(2, "chat_fancy");
		saveStatement.setInt(3, settings.isChatFancy() ? 1 : 0);
		saveStatement.addBatch();

		saveStatement.setString(2, "private_chat_split");
		saveStatement.setInt(3, settings.isPrivateChatSplit() ? 1 : 0);
		saveStatement.addBatch();

		saveStatement.setString(2, "accepting_aid");
		saveStatement.setInt(3, settings.isAcceptingAid() ? 1 : 0);
		saveStatement.addBatch();

		saveStatement.executeBatch();
	}

}
