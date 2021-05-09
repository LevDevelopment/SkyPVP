package net.noboplay.skypvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.core.api.UUIDFetcher;
import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.StatsUtil;

public class StatsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (args.length == 0) {
				double d = StatsUtil.getDeaths(player.getUniqueId().toString());
				double k = StatsUtil.getKills(player.getUniqueId().toString());
				double kd;
				if (StatsUtil.getDeaths(player.getUniqueId().toString()) == 0) {
					kd = k;
				} else {
					kd = k / d;
					kd *= 100.0D;
					kd = Math.round(kd);
					kd /= 100.0D;
				}
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDeine Stats");
				player.sendMessage("§2Kills §7» §3" + StatsUtil.getKills(player.getUniqueId().toString()));
				player.sendMessage("§2Deaths §7» §3" + StatsUtil.getDeaths(player.getUniqueId().toString()));
				player.sendMessage("§2K/D §7» §3" + Double.valueOf(kd));
				return true;
			}
			if (args.length == 1) {
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
				if (!StatsUtil.playerExsits(uuid))
					StatsUtil.createPlayer(uuid);
				double kd;
				double d = StatsUtil.getDeaths(uuid);
				double k = StatsUtil.getKills(uuid);
				if (StatsUtil.getDeaths(uuid) == 0) {
					kd = k;
				} else {
					kd = k / d;
					kd *= 100.0D;
					kd = Math.round(kd);
					kd /= 100.0D;
				}
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§aStats von §3" + args[0].trim());
				player.sendMessage("§2Kills §7» §3" + StatsUtil.getKills(uuid));
				player.sendMessage("§2Deaths §7» §3" + StatsUtil.getDeaths(uuid));
				player.sendMessage("§2K/D §7» §3" + Double.valueOf(kd));
				return true;
			} else {
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§cZuviele Argumente!");
			}
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
