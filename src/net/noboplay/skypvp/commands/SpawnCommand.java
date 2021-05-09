package net.noboplay.skypvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.SpawnUtil;

public class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			final Player player = (Player) commandSender;
			if (SkyPVP.getInstance().spawntask.containsKey(player.getName()))
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§cEs läuft bereits ein Vorgang!");
			else if (SkyPVP.getInstance().logtask.containsKey(player.getName()))
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu darfst den Command nicht im Combat ausführen!");
			else {
				SkyPVP.getInstance().spawn.put(player.getName(), player.getLocation().toVector());
				SkyPVP.getInstance().spawntask.put(player.getName(), new BukkitRunnable() {

					@Override
					public void run() {
						SkyPVP.getInstance().spawntask.remove(player.getName());
						SkyPVP.getInstance().spawn.remove(player.getName());
						SpawnUtil.tpSpawn(player);
						if (SkyPVP.getInstance().killstreak.get(player.getName()) != null)
							if (SkyPVP.getInstance().killstreak.get(player.getName()) > 0)
								player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDeine Killstreak wurde reseted.");
						SkyPVP.getInstance().killstreak.put(player.getName(), 0.0);
					}
				});
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu wirst in 3 Sekunden zum Spawn teleportiert!");
				SkyPVP.getInstance().spawntask.get(player.getName()).runTaskLater(SkyPVP.getInstance(), 60);
			}
		}
		return true;
	}

}
