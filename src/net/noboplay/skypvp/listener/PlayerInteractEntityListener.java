package net.noboplay.skypvp.listener;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.KitUtil;

public class PlayerInteractEntityListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if (event.getRightClicked() instanceof Villager) {
			Villager villager = (Villager) event.getRightClicked();
			if (villager.getCustomName().equalsIgnoreCase("§3SkyPVP§7-§bKits")) {
				event.setCancelled(true);
				if (SkyPVP.getInstance().kit.containsKey(player.getName())) {
					long diff = (System.currentTimeMillis() - ((Long) SkyPVP.getInstance().kit.get(player.getName())).longValue()) / 20L
							/ 60L;
					if (diff < 30) {
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu musst noch §a" + (30 - diff)
								+ ((30 - diff) == 1 ? " §cSekunde" : " §cSekunden") + " warten!");
						return;
					}
				}
				KitUtil.openKitInventory(player);
				SkyPVP.getInstance().kit.put(player.getName(), System.currentTimeMillis());
			}
		}
	}

}
