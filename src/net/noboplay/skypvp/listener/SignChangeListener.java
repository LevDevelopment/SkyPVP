package net.noboplay.skypvp.listener;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import net.noboplay.skypvp.SkyPVP;

public class SignChangeListener implements Listener {

	@EventHandler
	public void onChange(SignChangeEvent event) {
		Player player = event.getPlayer();

		if (event.getLine(0).equalsIgnoreCase("[Free]")) {
			if (!player.hasPermission("server.build")) {
				player.sendMessage(SkyPVP.getInstance().PREFIX + SkyPVP.getInstance().NOPERM);
				event.getBlock().setType(Material.AIR);
				return;
			}
			if (event.getLine(1).equals("")) {
				player.sendMessage(SkyPVP.getInstance().PREFIX + "§4In der 2. Zeile muss eine ID angegeben werden!");
				event.getBlock().setType(Material.AIR);
				return;
			}
			event.setLine(0, "§7[§3SkyPVP§7]");
			event.setLine(3, "§7Gönn dir!");
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§aSchild wurde erstellt!");
			Sign sign = (Sign) event.getBlock().getState();
			sign.update(true);
		}
	}

}
