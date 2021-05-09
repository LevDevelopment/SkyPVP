package net.noboplay.skypvp.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.noboplay.skypvp.SkyPVP;

public class PlayerCommandPreprocessListener implements Listener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		if (event.getMessage().startsWith("/kill")) {
			event.setCancelled(true);
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu darfst diesen Command nicht nutzen!");
		}
		if (event.getMessage().equalsIgnoreCase("/rl") || event.getMessage().equalsIgnoreCase("/reload")) {
			event.setCancelled(true);
			if (!player.isOp()) {
				player.sendMessage(SkyPVP.getInstance().PREFIX + SkyPVP.getInstance().NOPERM);
			} else {
				Bukkit.broadcastMessage(SkyPVP.getInstance().PREFIX + "§4Alle Daten werden neugeladen!");
				Bukkit.reload();
				Bukkit.broadcastMessage(SkyPVP.getInstance().PREFIX + "§aAlle Daten wurden neugeladen!");
			}
		}
		if (event.getMessage().startsWith("/heal")) {
			event.setCancelled(true);
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§4Ich denke nicht...");
		}
		if (event.getMessage().startsWith("/god")) {
			event.setCancelled(true);
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§4God ist deaktiviert!");
		}
	}

}
