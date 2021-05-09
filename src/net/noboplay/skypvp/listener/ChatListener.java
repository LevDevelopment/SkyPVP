package net.noboplay.skypvp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.noboplay.skypvp.SkyPVP;

public class ChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (SkyPVP.getInstance().spectate.contains(player.getName()))
			event.setCancelled(true);
	}

}
