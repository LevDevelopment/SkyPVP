package net.noboplay.skypvp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TeamManager {
	
	private List<Team> registeredTeams;

	public TeamManager() {
		this.setRegisteredTeams(new ArrayList<Team>());
	}

	public Player getTeamMate(Player player) {
		for (Team team : this.getRegisteredTeams()) {
			UUID uuid = player.getUniqueId();
			if (uuid.equals(team.getFirstUUID())) {
				return Bukkit.getPlayer(team.getSecondUUID());
			} else if (uuid.equals(team.getSecondUUID())) {
				return Bukkit.getPlayer(team.getFirstUUID());
			}
		}
		return null;
	}

	public Team getTeam(Player player) {
		for (Team team : this.getRegisteredTeams()) {
			UUID uuid = player.getUniqueId();
			if (uuid.equals(team.getFirstUUID()) || uuid.equals(team.getSecondUUID())) {
				return team;
			}
		}
		return null;
	}

	public boolean removePlayerTeam(OfflinePlayer player) {
		for (Team team : this.getRegisteredTeams()) {
			UUID uuid = player.getUniqueId();
			if (uuid.equals(team.getFirstUUID()) || uuid.equals(team.getSecondUUID())) {
				return this.getRegisteredTeams().remove(team);
			}
		}
		return false;
	}

	public List<Team> getRegisteredTeams() {
		return registeredTeams;
	}

	public void setRegisteredTeams(List<Team> registeredTeams) {
		this.registeredTeams = registeredTeams;
	}

}
