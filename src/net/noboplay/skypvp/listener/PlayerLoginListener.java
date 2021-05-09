package net.noboplay.skypvp.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLoginListener implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()) {
			if (player.hasPermission("server.joinfull")) {
				event.allow();
			} else {
				event.disallow(Result.KICK_FULL,
						"§cDer Server ist voll.\n§bKaufe dir für nur 20€ einmalig §6Premium: §ahttp://store.noboplay.net/");
			}
		}
	}
}
