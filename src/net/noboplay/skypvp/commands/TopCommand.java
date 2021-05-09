package net.noboplay.skypvp.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.MySQL;

public class TopCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			int i = 1;
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§3Ranking");
			player.sendMessage("§6#      Kills    Deaths   K/D   Name");
			try {
				ResultSet rs = MySQL.getConnection()
						.prepareStatement("SELECT * FROM Stats ORDER BY KILLS DESC LIMIT 10;").executeQuery();
				while (rs.next()) {
					String name = Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("UUID"))).getName();
					int k = rs.getInt("KILLS");
					int d = rs.getInt("DEATHS");
					double kd;
					if (d == 0) {
						kd = k;
					} else {
						kd = rs.getDouble("KILLS") / rs.getDouble("DEATHS");
						kd *= 100.0D;
						kd = Math.round(kd);
						kd /= 100.0D;
					}
					player.sendMessage(
							"§b#" + i++ + "   " + k + "     " + d + "         " + Double.valueOf(kd) + "    " + name);
				}
			} catch (SQLException e) {
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§cEs ist ein Fehler aufgetreten.");
				player.sendMessage("§c" + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}

}
