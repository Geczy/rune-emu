package net.scapeemulator.game.io.jdbc;

import java.io.IOException;
import java.sql.SQLException;

import net.scapeemulator.game.model.player.Player;

public abstract class Table {

	public abstract void load(Player player) throws SQLException, IOException;

	public abstract void save(Player player) throws SQLException, IOException;

}
