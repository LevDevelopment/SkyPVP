package net.noboplay.skypvp.listener;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.noboplay.core.api.ActionBar;
import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.RegionUtil;
import net.noboplay.skypvp.util.Variables;

public class EntityDamageByEntityListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			final Player player = (Player) event.getEntity();
			if (event.getDamager() instanceof EnderPearl) {
				event.setCancelled(true);
			}
			if (RegionUtil.isInRegion(player)) {
				event.setCancelled(true);
				return;
			}
			if (event.getDamager() instanceof Player) {
				final Player damager = (Player) event.getDamager();
				if (Variables.getTeamManager().getTeam(damager) != null) {
					if (Variables.getTeamManager().getTeamMate(damager) == player) {
						damager.sendMessage(
								SkyPVP.getInstance().PREFIX + "§cDu kannst deinen Teampartner nicht schlagen!");
						event.setCancelled(true);
						return;
					}
				}
				if (RegionUtil.isInRegion(damager)) {
					event.setCancelled(true);
					return;
				}
				if (!SkyPVP.getInstance().log.contains(player.getName())) {
					SkyPVP.getInstance().log.add(player.getName());
					ActionBar.sendActionBar(player, "§cDu bist nun im Kampf, logge dich nicht aus!");
					final String name = player.getName();
					SkyPVP.getInstance().logtask.put(player.getName(), new BukkitRunnable() {

						@Override
						public void run() {
							cancel();
							SkyPVP.getInstance().log.remove(name);
							if (player != null)
								ActionBar.sendActionBar(player, "§aDu bist nicht mehr im Kampf.");
							SkyPVP.getInstance().logtask.remove(name);
						}
					});
					SkyPVP.getInstance().logtask.get(player.getName()).runTaskLaterAsynchronously(SkyPVP.getInstance(),
							20 * 15);
				}
				if (!SkyPVP.getInstance().log.contains(damager.getName())) {
					SkyPVP.getInstance().log.add(damager.getName());
					final String name = damager.getName();
					ActionBar.sendActionBar(damager, "§cDu bist nun im Kampf, logge dich nicht aus!");
					SkyPVP.getInstance().logtask.put(damager.getName(), new BukkitRunnable() {

						@Override
						public void run() {
							cancel();
							SkyPVP.getInstance().log.remove(name);
							if (player != null)
								ActionBar.sendActionBar(damager, "§aDu bist nicht mehr im Kampf.");
							SkyPVP.getInstance().logtask.remove(name);
						}
					});
					SkyPVP.getInstance().logtask.get(damager.getName()).runTaskLaterAsynchronously(SkyPVP.getInstance(),
							20 * 15);
				}
			}
		}
		if (event.getEntity() instanceof Villager) {
			if (event.getDamager() instanceof Player) {
				Player player = (Player) event.getDamager();
				if (!SkyPVP.getInstance().build.contains(player.getName())) {
					event.setCancelled(true);
				} else {
					event.setCancelled(false);
				}
			} else {
				event.setCancelled(true);
			}
		}
	}

}
