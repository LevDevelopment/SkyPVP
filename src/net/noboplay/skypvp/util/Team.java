package net.noboplay.skypvp.util;

import java.util.UUID;

import org.bukkit.OfflinePlayer;

public class Team {

	private UUID firstPlayer;
	private UUID secondPlayer;
	private long created;

	public Team(UUID firstPlayer, UUID secondPlayer) {
		this.setFirstPlayer(firstPlayer);
		this.setSecondPlayer(secondPlayer);
		this.setCreated(System.currentTimeMillis());
	}

	public boolean containsPlayer(OfflinePlayer player) {
		UUID uuid = player.getUniqueId();
		if (this.getFirstUUID().equals(uuid) || this.getSecondUUID().equals(uuid)) {
			return true;
		}
		return false;
	}

	public UUID getFirstUUID() {
		return this.firstPlayer;
	}

	public void setFirstPlayer(UUID firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public UUID getSecondUUID() {
		return this.secondPlayer;
	}

	public void setSecondPlayer(UUID secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

}
