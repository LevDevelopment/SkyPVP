package net.noboplay.skypvp.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsUtil {

	public static boolean playerExsits(String uuid) {
		try {
			PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * FROM Stats WHERE UUID=?");
			statement.setString(1, uuid);
			ResultSet resultset = statement.executeQuery();
			if (resultset.next()) {
				return resultset.getString("UUID") != null;
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public static void createPlayer(String uuid) {
		if (!(playerExsits(uuid))) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("INSERT INTO Stats(UUID, KILLS, DEATHS) VALUES (?, 0, 0)");
				statement.setString(1, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getKills(String uuid) {
		int i = 0;
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("SELECT * FROM Stats WHERE UUID=?");
				statement.setString(1, uuid);
				ResultSet resultset = statement.executeQuery();
				if (!resultset.next() || Integer.valueOf(resultset.getInt("KILLS")) == null) {
					i = 0;
				}
				i = resultset.getInt("KILLS");
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getKills(uuid);
		}
		return i;
	}

	public static int getDeaths(String uuid) {
		int i = 0;
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("SELECT * FROM Stats WHERE UUID=?");
				statement.setString(1, uuid);
				ResultSet rs = statement.executeQuery();
				if (!rs.next() || Integer.valueOf(rs.getInt("DEATHS")) == null) {
					i = 0;
				}
				i = rs.getInt("DEATHS");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getDeaths(uuid);
		}
		return i;
	}

	public static void addKills(String uuid, int amount) {
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("UPDATE Stats SET KILLS=? WHERE UUID=?;");
				statement.setInt(1, getKills(uuid) + amount);
				statement.setString(2, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			addKills(uuid, amount);
		}
	}

	public static void removeKills(String uuid, int amount) {
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("UPDATE Stats SET KILLS=? WHERE UUID=?;");
				statement.setInt(1, getKills(uuid) - amount);
				statement.setString(2, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			removeKills(uuid, amount);
		}
	}

	public static void setKills(String uuid, int amount) {
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("UPDATE Stats SET KILLS=? WHERE UUID=?;");
				statement.setInt(1, amount);
				statement.setString(2, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			setKills(uuid, amount);
		}
	}

	public static void addDeaths(String uuid, int amount) {
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("UPDATE Stats SET DEATHS=? WHERE UUID=?;");
				statement.setInt(1, getDeaths(uuid) + amount);
				statement.setString(2, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			addDeaths(uuid, amount);
		}
	}

	public static void removeDeaths(String uuid, int amount) {
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("UPDATE Stats SET DEATHS=? WHERE UUID=?;");
				statement.setInt(1, getDeaths(uuid) - amount);
				statement.setString(2, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			removeDeaths(uuid, amount);
		}
	}

	public static void setDeaths(String uuid, int amount) {
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("UPDATE Stats SET DEATHS=? WHERE UUID=?;");
				statement.setInt(1, amount);
				statement.setString(2, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			setDeaths(uuid, amount);
		}
	}

}
