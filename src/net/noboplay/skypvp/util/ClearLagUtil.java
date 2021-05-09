package net.noboplay.skypvp.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import net.noboplay.skypvp.SkyPVP;

public class ClearLagUtil {

	static int time;

	public static void startCountdown() {
		time = 3;
		Bukkit.getScheduler().runTaskTimerAsynchronously(SkyPVP.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (time == 3) {
					Bukkit.broadcastMessage(SkyPVP.getInstance().PREFIX
							+ "§cAlle am Boden liegenden Items werden in 60 Sekunden entfernt.");
				}

				if (time == 2) {
					Bukkit.broadcastMessage(SkyPVP.getInstance().PREFIX
							+ "§cAlle am Boden liegenden Items werden in 30 Sekunden entfernt.");
				}

				if (time == 1) {
					int entitys = 0;
					for (Entity entity : Bukkit.getWorld("skypvp").getEntities()) {
						if (!(entity instanceof Player) && !(entity instanceof Villager)
								&& !(entity instanceof EnderPearl)) {
							entitys++;
							entity.remove();
						}
					}
					Bukkit.broadcastMessage(SkyPVP.getInstance().PREFIX + "§aEs "
							+ (entitys == 1 ? "wurde §61 §aItem" : "wurden §6" + entitys + " §aItems") + " entfernt!");
				}

				if (time == 0) {
					time = 3;
				}
				time--;
			}
		}, 0, 20 * 30);
	}

}