package net.noboplay.skypvp.util;

import java.util.Random;

public class Variables {
	
	private static Random random = new Random();
	private static TeamManager manager = new TeamManager();
	
	public static Random getRandom() {
		return random;
	}
	
	public static TeamManager getTeamManager() {
		return manager;
	}

}
