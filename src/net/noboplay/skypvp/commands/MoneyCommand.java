package net.noboplay.skypvp.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.core.api.UUIDFetcher;
import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.MoneyUtil;

public class MoneyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (args.length == 0) {
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu besitzt §b"
						+ MoneyUtil.getMoney(player.getUniqueId().toString())
						+ (MoneyUtil.getMoney(player.getUniqueId().toString()) == 1 ? " §aNobo" : " §aNobos") + ".");
			} else if (args.length == 1) {
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDie Stats werden geladen...");
				Player target = getPlayerByDisplayName(args[0].trim());
				String uuid;
				if (target == null) {
					try {
						uuid = UUIDFetcher.getUUID(args[0].trim()).toString();
					} catch (Exception e) {
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDieser Username existiert nicht.");
						return true;
					}
				} else {
					uuid = target.getUniqueId().toString();
				}
				if (!MoneyUtil.playerExsits(uuid))
					MoneyUtil.createPlayer(uuid);
				player.sendMessage(SkyPVP.getInstance().PREFIX
						+ (target != null ? target.getDisplayName()
								: "§e " + UUIDFetcher.getName(UUID.fromString(uuid)))
						+ " besitzt §b" + MoneyUtil.getMoney(uuid)
						+ (MoneyUtil.getMoney(uuid) == 1 ? " §aNobo" : " §aNobos") + ".");
			} else
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§c/money (NAME)");
		}
		return true;
	}

	private Player getPlayerByDisplayName(String displayName) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (ChatColor.stripColor(player.getDisplayName()).equalsIgnoreCase(displayName)) {
				return player;
			}
		}
		return null;
	}

}
