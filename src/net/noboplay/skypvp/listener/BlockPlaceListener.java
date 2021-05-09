package net.noboplay.skypvp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import net.noboplay.skypvp.SkyPVP;

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (SkyPVP.getInstance().build.contains(player.getName())) {
			return;
		}
		event.setCancelled(true);
		event.setBuild(false);
	}

}
