package net.noboplay.skypvp.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.noboplay.core.api.Nametags;
import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.KitUtil;
import net.noboplay.skypvp.util.MoneyUtil;
import net.noboplay.skypvp.util.ScoreboardUtil;
import net.noboplay.skypvp.util.SpawnUtil;
import net.noboplay.skypvp.util.StatsUtil;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(null);
		player.sendMessage(SkyPVP.getInstance().PREFIX + "§cTeams über 2 Leuten werden mit einem Bann bestraft!");
		player.setGameMode(GameMode.SURVIVAL);
		player.setHealth(20);
		player.setFoodLevel(20);
		if (!StatsUtil.playerExsits(player.getUniqueId().toString()))
			StatsUtil.createPlayer(player.getUniqueId().toString());
		if (!MoneyUtil.playerExsits(player.getUniqueId().toString()))
			MoneyUtil.createPlayer(player.getUniqueId().toString());
		SpawnUtil.tpSpawn(player);
		if (!(player.hasPlayedBefore())) {
			KitUtil.giveKit1(player);
		}
		Nametags.setPlayerListName(player);
		for (String spectate : SkyPVP.getInstance().spectate) {
			player.hidePlayer(Bukkit.getPlayer(spectate));
		}
		ScoreboardUtil.updateScoreboard(player);
		if (KitUtil.respawn.get(player.getName()) == null) {
			KitUtil.setRespawnKit(player, 1);
		}
	}

}
