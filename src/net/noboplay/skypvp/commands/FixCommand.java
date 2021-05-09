package net.noboplay.skypvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.skypvp.SkyPVP;

public class FixCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (args.length == 0) {
				if (ChatColor.stripColor(player.getDisplayName()).equalsIgnoreCase(player.getName())
						&& !SkyPVP.getInstance().spectate.contains(player)) {
					for (Player players : Bukkit.getOnlinePlayers()) {
						players.hidePlayer(player);
						players.showPlayer(player);
					}
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast dich gefixt.");
				} else
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu kannst dich nicht fixen.");
			} else
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§cZuviele Argumente.");

		}
		return true;
	}

}
