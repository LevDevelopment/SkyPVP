package net.noboplay.skypvp.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.noboplay.skypvp.SkyPVP;

public class SpawnUtil {

	public static void setSpawn(Location loc) {
		File file = new File("plugins/SkyPVP", "spawn.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		double yaw = loc.getYaw();
		double pitch = loc.getPitch();
		String world = loc.getWorld().getName();

		cfg.set("X", Double.valueOf(x));
		cfg.set("Y", Double.valueOf(y));
		cfg.set("Z", Double.valueOf(z));
		cfg.set("Yaw", Double.valueOf(yaw));
		cfg.set("Pitch", Double.valueOf(pitch));
		cfg.set("World", world);

		try {
			cfg.save(file);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void tpSpawn(Player p) {
		File file = new File("plugins/SkyPVP", "spawn.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		Location spawnLocation = new Location(Bukkit.getWorld(cfg.getString("World")), cfg.getDouble("X"),
				cfg.getDouble("Y"), cfg.getDouble("Z"), (float) cfg.getDouble("Yaw"), (float) cfg.getDouble("Pitch"));
		p.teleport(spawnLocation);
	}

	public static Location getSpawn() {
		File file = new File("plugins/SkyPVP", "spawn.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		Location spawnLocation = new Location(Bukkit.getWorld(cfg.getString("World")), cfg.getDouble("X"),
				cfg.getDouble("Y"), cfg.getDouble("Z"), (float) cfg.getDouble("Yaw"), (float) cfg.getDouble("Pitch"));
		return spawnLocation;
	}

	public static void startAskingScheduler() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(SkyPVP.getInstance(), new Runnable() {

			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (SkyPVP.getInstance().spawn.containsKey(player.getName())) {
						if (player.getLocation().getBlockX() != SkyPVP.getInstance().spawn.get(player.getName())
								.getBlockX()
								|| player.getLocation().getBlockZ() != SkyPVP.getInstance().spawn.get(player.getName())
										.getBlockZ()
								|| player.getLocation().getBlockY() != SkyPVP.getInstance().spawn.get(player.getName())
										.getBlockY()) {
							SkyPVP.getInstance().spawn.remove(player.getName());
							SkyPVP.getInstance().spawntask.get(player.getName()).cancel();
							SkyPVP.getInstance().spawntask.remove(player.getName());
							player.sendMessage(
									SkyPVP.getInstance().PREFIX + "§cDie Teleportation zum Spawn wurde abgebrochen!");
						}
					}
				}
			}
		}, 0, 10);
	}
}
