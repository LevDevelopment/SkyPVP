package net.noboplay.skypvp.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.noboplay.skypvp.util.RegionUtil;
import net.noboplay.skypvp.util.SpawnUtil;

public class EntityDamageListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (RegionUtil.isInRegion(player)) {
				event.setCancelled(true);
			}
			if (event.getCause() == DamageCause.FALL) {
				event.setCancelled(true);
			}
			if (event.getCause() == DamageCause.VOID) {
				if (player.getGameMode() == GameMode.SPECTATOR) {
					event.setCancelled(true);
					SpawnUtil.tpSpawn(player);
				}
			}
		}
	}

}
