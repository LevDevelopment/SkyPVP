package net.noboplay.skypvp.util;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.noboplay.skypvp.SkyPVP;

public class RegionFileManager {

	static File file = new File("plugins/SkyPVP/" + "region.yml");
	static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	public static void saveCfg() {
		try {
			cfg.save(file);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void addRegion(Location loc1, Location loc2) {
		int id = cfg.getKeys(false).size();
		cfg.set(id + ".pos1.world", loc1.getWorld().getName());
		cfg.set(id + ".pos1.x", loc1.getBlockX());
		cfg.set(id + ".pos1.y", loc1.getBlockY());
		cfg.set(id + ".pos1.z", loc1.getBlockZ());

		cfg.set(id + ".pos2.world", loc2.getWorld().getName());
		cfg.set(id + ".pos2.x", loc2.getBlockX());
		cfg.set(id + ".pos2.y", loc2.getBlockY());
		cfg.set(id + ".pos2.z", loc2.getBlockZ());
		saveCfg();
	}

	public static void registerRegion() {
		for (int i = 0; i < cfg.getKeys(false).size(); i++) {
			SkyPVP.getInstance().region.add(new Cuboid(getLoc(i, "pos1"), getLoc(i, "pos2")));
		}
	}

	public static Location getLoc(int id, String name) {
		return new Location(Bukkit.getWorld(cfg.getString(id + "." + name + ".world")),
				cfg.getInt(id + "." + name + ".x"), cfg.getInt(id + "." + name + ".y"),
				cfg.getInt(id + "." + name + ".z"));
	}

}
