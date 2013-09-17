package net.scapeemulator.game.io.jdbc;

import net.scapeemulator.game.model.player.Player;

import java.io.IOException;
import java.sql.SQLException;

public abstract class Table {

	public abstract void load(Player player) throws SQLException, IOException;

	public abstract void save(Player player) throws SQLException, IOException;

}
