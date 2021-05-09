package net.noboplay.skypvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.skypvp.SkyPVP;

public class BuildCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (player.hasPermission("server.build")) {
				if (SkyPVP.getInstance().build.contains(player.getName())) {
					SkyPVP.getInstance().build.remove(player.getName());
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§bDu bist nun nicht mehr im Buildmodus!");
				} else {
					SkyPVP.getInstance().build.add(player.getName());
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§bDu bist nun im Buildmodus!");
				}
			}
		}
		return true;
	}

}
