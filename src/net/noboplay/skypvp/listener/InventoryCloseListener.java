package net.noboplay.skypvp.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

public class InventoryCloseListener implements Listener {

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if (event.getInventory() instanceof EnchantingInventory) {
			event.getInventory().setItem(1, new ItemStack(Material.AIR));
		}
	}

}
