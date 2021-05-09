package net.noboplay.skypvp.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;

public class MoneyUtil {

	public static boolean playerExsits(String uuid) {
		try {
			PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * FROM Money WHERE UUID=?");
			statement.setString(1, uuid);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getString("UUID") != null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void createPlayer(String uuid) {
		if (!(playerExsits(uuid))) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("INSERT INTO Money(UUID, MONEY) VALUES (?, 0)");
				statement.setString(1, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getMoney(String uuid) {
		int i = 0;
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("SELECT * FROM Money WHERE UUID=?");
				statement.setString(1, uuid);
				ResultSet rs = statement.executeQuery();
				if (!rs.next() || Integer.valueOf(rs.getInt("MONEY")) == null)
					;
				i = rs.getInt("MONEY");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getMoney(uuid);
		}
		return i;
	}

	public static void addMoney(String uuid, int amount) {
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("UPDATE Money SET MONEY=? WHERE UUID=?;");
				statement.setInt(1, getMoney(uuid) + amount);
				statement.setString(2, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			addMoney(uuid, amount);
		}
	}

	public static void removeMoney(String uuid, int amount) {
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("UPDATE Money SET MONEY=? WHERE UUID=?;");
				statement.setInt(1, getMoney(uuid) - amount);
				statement.setString(2, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			removeMoney(uuid, amount);
		}
		if (Bukkit.getPlayer(UUID.fromString(uuid)) != null)
			ScoreboardUtil.updateScoreboard(Bukkit.getPlayer(UUID.fromString(uuid)));
	}

	public static void setMoney(String uuid, int amount) {
		if (playerExsits(uuid)) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("UPDATE Money SET MONEY=? WHERE UUID=?;");
				statement.setInt(1, amount);
				statement.setString(2, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			setMoney(uuid, amount);
		}
	}

	public static boolean hasEnoughMoney(String uuid, int amount) {
		if (getMoney(uuid) >= amount) {
			return true;
		}
		return false;
	}

}
