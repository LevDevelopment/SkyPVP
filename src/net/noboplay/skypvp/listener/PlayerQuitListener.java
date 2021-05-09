package net.noboplay.skypvp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.noboplay.skypvp.SkyPVP;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(null);
		if (SkyPVP.getInstance().log.contains(player.getName())) {
			SkyPVP.getInstance().logtask.get(player.getName()).cancel();
			SkyPVP.getInstance().logtask.get(player.getName()).run();
			player.damage(21);
		}
		if (SkyPVP.getInstance().killstreak.containsKey(player.getName())) {
			SkyPVP.getInstance().killstreak.put(player.getName(), 0.0);
		}
		if (SkyPVP.getInstance().spectate.contains(player.getName())) {
			SkyPVP.getInstance().spectate.remove(player.getName());
		}
	}

}
