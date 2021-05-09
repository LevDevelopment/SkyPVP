package net.noboplay.skypvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.SpawnUtil;

public class SpectateCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (player.hasPermission("server.team")) {
				if (SkyPVP.getInstance().spectate.contains(player.getName())) {
					SkyPVP.getInstance().spectate.remove(player.getName());
					SpawnUtil.tpSpawn(player);
					player.setGameMode(GameMode.SURVIVAL);
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.showPlayer(player);
					}
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu bist nun wieder sichtbar!");
				} else {
					SkyPVP.getInstance().spectate.add(player.getName());
					player.setGameMode(GameMode.SPECTATOR);
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.hidePlayer(player);
					}
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu bist nun unsichtbar!");
				}
			} else {
				player.sendMessage(SkyPVP.getInstance().PREFIX + SkyPVP.getInstance().NOPERM);
			}
		}
		return true;
	}

}
