package net.noboplay.skypvp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.noboplay.skypvp.SkyPVP;

public class MySQL {

	private static Connection connection;

	public static void connect() {
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + SkyPVP.getInstance().getConfig().getString("MySQL.Host") + ":"
							+ SkyPVP.getInstance().getConfig().getInt("MySQL.Port") + "/"
							+ SkyPVP.getInstance().getConfig().getString("MySQL.Database") + "?autoReconnect=true",
					SkyPVP.getInstance().getConfig().getString("MySQL.User"),
					SkyPVP.getInstance().getConfig().getString("MySQL.Password"));
			System.out.println("SkyPVP » MySQL-Verbindung wurde aufgebaut!");
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	public static void close() {
		try {
			if (connection != null) {
				connection.close();
				System.out.println("SkyPVP » MySQL-Verbindung wurde geschlossen!");
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}

}
