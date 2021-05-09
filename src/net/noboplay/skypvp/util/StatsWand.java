package net.noboplay.skypvp.util;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class StatsWand {

	static File file = new File("plugins/SkyPVP", "heads.yml");
	static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	static HashMap<Integer, String> rang = new HashMap<Integer, String>();

	public static void set() {
		int in = 0;
		try {
			ResultSet rs = MySQL.getConnection().prepareStatement("SELECT UUID FROM Stats ORDER BY Kills DESC LIMIT 3")
					.executeQuery();
			while (rs.next()) {
				in++;
				rang.put(in, rs.getString("UUID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (rang.size() < 3) {
			System.out.println(
					"SkyPVP » Es waren weniger als 3 Spieler online, deswegen konnte die Stats-Wand nicht geladen werden!");
			return;
		}

		Location loc1 = new Location(Bukkit.getWorld(cfg.getString("Head1.World")), cfg.getDouble("Head1.X"),
				cfg.getDouble("Head1.Y"), cfg.getDouble("Head1.Z"), (float) cfg.getDouble("Head1.Yaw"),
				(float) cfg.getDouble("Head1.Pitch"));
		Location loc2 = new Location(Bukkit.getWorld(cfg.getString("Head2.World")), cfg.getDouble("Head2.X"),
				cfg.getDouble("Head2.Y"), cfg.getDouble("Head2.Z"), (float) cfg.getDouble("Head2.Yaw"),
				(float) cfg.getDouble("Head2.Pitch"));
		Location loc3 = new Location(Bukkit.getWorld(cfg.getString("Head3.World")), cfg.getDouble("Head3.X"),
				cfg.getDouble("Head3.Y"), cfg.getDouble("Head3.Z"), (float) cfg.getDouble("Head3.Yaw"),
				(float) cfg.getDouble("Head3.Pitch"));

		List<Location> LOC = new ArrayList<Location>();
		LOC.add(loc1);
		LOC.add(loc2);
		LOC.add(loc3);

		for (int i = 0; i < LOC.size(); i++) {
			int id = i + 1;

			LOC.get(i).getBlock().setType(Material.SKULL);
			Skull s = (Skull) LOC.get(i).getBlock().getState();
			s.setSkullType(SkullType.PLAYER);
			s.setRotation(BlockFace.EAST);

			String name = Bukkit.getOfflinePlayer(UUID.fromString(rang.get(id))).getName();

			s.setOwner(name);
			s.update();

			Location newloc = new Location(LOC.get(i).getWorld(), LOC.get(i).getX(), LOC.get(i).getY() - 1,
					LOC.get(i).getZ());

			if (newloc.getBlock().getState() instanceof Sign) {
				BlockState b = newloc.getBlock().getState();
				Sign S = (Sign) b;

				S.setLine(0, "#" + id);
				S.setLine(1, name);
				S.setLine(2, "");
				S.setLine(3, StatsUtil.getKills(rang.get(id)) + " Kills");
				S.update();
			}
		}
	}

}
