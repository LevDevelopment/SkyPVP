package net.noboplay.skypvp.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.skypvp.util.ItemBuilder;

public class EggCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (player.hasPermission("server.admin")) {
				player.getInventory().addItem(
						ItemBuilder.createItem("§3SkyPVP§7-§bKits", "", Material.MONSTER_EGG, 1, 120));
			}
		}
		return true;
	}

}
