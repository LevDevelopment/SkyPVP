package net.noboplay.skypvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.skypvp.SkyPVP;

public class EnderchestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (player.hasPermission("server.admin")) {
				if (args.length == 1) {
					Player target = Bukkit.getPlayer(args[0].trim());
					if (target == null) {
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDer Spieler ist nicht online!");
						return true;
					}
					player.openInventory(target.getEnderChest());
				} else {
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§b/enderchest <NAME>");
				}
			} else {
				player.sendMessage(SkyPVP.getInstance().PREFIX + SkyPVP.getInstance().NOPERM);
			}
		}
		return true;
	}

}
