package net.noboplay.skypvp.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.KitUtil;
import net.noboplay.skypvp.util.ScoreboardUtil;
import net.noboplay.skypvp.util.SpawnUtil;
import net.noboplay.skypvp.util.StatsWand;

public class PlayerRespawnListener implements Listener {

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		player.setGameMode(GameMode.SURVIVAL);
		player.setHealth(20);
		player.setFoodLevel(20);
		SpawnUtil.tpSpawn(player);
		ScoreboardUtil.updateScoreboard(player);
		switch (KitUtil.getRespawnKit(player)) {
		case 1:
			KitUtil.giveKit1(player);
			break;
		case 2:
			KitUtil.giveKit2(player);
			break;
		case 3:
			KitUtil.giveKit3(player);
			break;
		case 4:
			KitUtil.giveKit4(player);
			break;
		case 5:
			KitUtil.giveKit5(player);
			break;
		case 6:
			KitUtil.giveKit6(player);
			Bukkit.getScheduler().runTaskLater(SkyPVP.getInstance(), new Runnable() {

				@Override
				public void run() {
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 0));
				}

			}, 10);
			break;
		case 7:
			KitUtil.giveKit7(player);
			break;
		case 8:
			KitUtil.giveKit8(player);
			Bukkit.getScheduler().runTaskLater(SkyPVP.getInstance(), new Runnable() {

				@Override
				public void run() {
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 0));
				}

			}, 10);
			break;
		case 9:
			KitUtil.giveKit9(player);
			break;
		case 10:
			KitUtil.giveKit10(player);
			break;
		case 11:
			KitUtil.giveKit11(player);
			break;
		case 12:
			KitUtil.giveKit12(player);
			break;
		default:
			KitUtil.giveKit1(player);
			break;
		}
		StatsWand.set();
	}

}
