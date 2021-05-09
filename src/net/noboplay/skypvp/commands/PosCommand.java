package net.noboplay.skypvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.skypvp.SkyPVP;

public class PosCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (player.hasPermission("server.admin")) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("1")) {
						SkyPVP.getInstance().pos1.put(player, player.getLocation());
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast die 1. Position gesetzt!");
					} else if (args[0].equalsIgnoreCase("2")) {
						SkyPVP.getInstance().pos2.put(player, player.getLocation());
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast die 2. Position gesetzt!");
					}
				} else {
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§b/pos <1|2>");
				}
			} else {
				player.sendMessage(SkyPVP.getInstance().PREFIX + SkyPVP.getInstance().NOPERM);
			}
		}
		return true;
	}

}
