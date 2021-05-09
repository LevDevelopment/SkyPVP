package net.noboplay.skypvp.listener;

import org.bukkit.DyeColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

public class InventoryOpenListener implements Listener {

	@EventHandler
	public void onOpen(InventoryOpenEvent event) {
		if (event.getInventory() instanceof EnchantingInventory) {
			EnchantingInventory inventory = (EnchantingInventory) event.getInventory();
			Dye dye = new Dye();
			dye.setColor(DyeColor.BLUE);
			ItemStack item = dye.toItemStack(20);
			inventory.setItem(1, item);
		}
	}

}
