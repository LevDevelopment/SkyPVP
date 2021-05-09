package net.noboplay.skypvp.util;

import org.bukkit.entity.Player;

import net.noboplay.skypvp.SkyPVP;

public class RegionUtil {
	
	public static boolean isInRegion(Player player) {
		for (Cuboid cuboid : SkyPVP.getInstance().region) {
			if (cuboid.containsLocation(player.getLocation())) {
				return true;
			}
		}
		return false;
	}

}
