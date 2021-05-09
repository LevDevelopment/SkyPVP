package net.noboplay.skypvp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;

public class BlockBurnListener implements Listener {

	@EventHandler
	public void onBurn(BlockBurnEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onIgnite(BlockIgniteEvent event) {
		event.setCancelled(true);
	}
}
