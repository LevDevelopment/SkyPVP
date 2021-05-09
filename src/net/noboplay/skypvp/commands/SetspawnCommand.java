package net.noboplay.skypvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.SpawnUtil;

public class SetspawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (player.hasPermission("server.admin")) {
				SpawnUtil.setSpawn(player.getLocation());
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast den Spawn gesetzt!");
			} else {
				player.sendMessage(SkyPVP.getInstance().PREFIX + SkyPVP.getInstance().NOPERM);
			}
		}
		return true;
	}

}
