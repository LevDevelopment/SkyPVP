package net.noboplay.skypvp.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.Team;
import net.noboplay.skypvp.util.TeamManager;
import net.noboplay.skypvp.util.Variables;

public class FriedenCommand implements CommandExecutor {

	private Set<String> timedOut = new HashSet<String>();
	private Map<String, String> requests = new HashMap<String, String>();

	private void startDelaying(final String player) {
		this.getTimedOut().add(player);
		new BukkitRunnable() {
			@Override
			public void run() {
				getTimedOut().remove(player);
				getRequests().remove(player);
			}
		}.runTaskLaterAsynchronously(SkyPVP.getInstance(), 200);
	}

	private void startLeaveDelay(final String player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				Team team = getManager().getTeam(getPlayerByDisplayName(player));
				if (getManager().getRegisteredTeams().remove(team)) {
					Player first = Bukkit.getPlayer(team.getFirstUUID());
					Player second = Bukkit.getPlayer(team.getSecondUUID());
					if (first != null) {
						first.sendMessage(SkyPVP.getInstance().PREFIX + "§cDas Team wurde aufgelöst!");
					}
					if (second != null) {
						second.sendMessage(SkyPVP.getInstance().PREFIX + "§cDas Team wurde aufgelöst!");
					}
				} else {
					if (Bukkit.getPlayer(player) != null) {
						Bukkit.getPlayer(player).sendMessage(SkyPVP.getInstance().PREFIX
								+ "§cKonnte das Team nicht auflösen, da es schon aufgelöst ist.");
					}
				}
			}
		}.runTaskLaterAsynchronously(SkyPVP.getInstance(), 200);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 2) {
				String type = args[0].trim();
				if (type.equalsIgnoreCase("add")) {
					askForTeam(player, args[1].trim());
					return true;
				} else if (type.equalsIgnoreCase("deny")) {
					askForDeny(player, args[1].trim());
					return true;
				} else if (type.equalsIgnoreCase("accept")) {
					askForAccept(player, args[1].trim());
					return true;
				}
			} else if (args.length == 1) {
				String type = args[0].trim();
				if (type.equalsIgnoreCase("info")) {
					sendInfo(player);
					return true;
				} else if (type.equalsIgnoreCase("leave")) {
					askForLeave(player);
					return true;
				}
			}
			player.sendMessage(
					SkyPVP.getInstance().PREFIX + "§7Verwende §c/peace <add|accept|deny|leave|info> [Spielername]");
			return true;
		}
		return true;
	}

	private void askForAccept(Player player, String sender) {
		Player playerSender = getPlayerByDisplayName(sender);
		if (sender == null) {
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDieser Spieler ist offline.");
			this.getTimedOut().remove(playerSender.getName());
			this.getRequests().remove(playerSender.getName());
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§cSeine Anfrage wurde abgebrochen.");
			return;
		}
		if (this.getRequests().containsKey(playerSender.getName())) {
			this.getRequests().remove(playerSender.getName());
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast erfolgreich die Anfrage von §e" + sender
					+ " §aangenommen!");
			playerSender.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu bist nun mit §e" + player.getDisplayName()
					+ " §ain einem Team!");
			this.getManager().getRegisteredTeams().add(new Team(player.getUniqueId(), playerSender.getUniqueId()));
			return;
		}
		player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast keine Anfrage von diesem Spieler!");
		return;
	}

	private void askForDeny(Player player, String sender) {
		Player playerSender = getPlayerByDisplayName(sender);
		if (playerSender == null) {
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDieser Spieler ist offline.");
			this.getTimedOut().remove(sender);
			this.getRequests().remove(sender);
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§cSeine Anfrage wurde abgebrochen.");
			return;
		}
		if (this.getRequests().containsKey(playerSender.getName())) {
			this.getRequests().remove(sender);
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast erfolgreich die Anfrage von §e" + sender
					+ " §cabgelehnt!");
			playerSender.sendMessage(
					SkyPVP.getInstance().PREFIX + "§e" + player.getName() + " §c hat deine Anfrage abgelehnt!");
			return;
		}
		player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast keine Anfrage von diesem Spieler!");
		return;
	}

	private void askForLeave(Player player) {
		Team team = this.getManager().getTeam(player);
		if (team == null) {
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu bist in keinem Team!");
			return;
		}
		Player first = Bukkit.getPlayer(team.getFirstUUID());
		Player second = Bukkit.getPlayer(team.getSecondUUID());
		if (first != null) {
			first.sendMessage(SkyPVP.getInstance().PREFIX + "§cDas Team wird in §e10 Sekunden §caufgelöst!");
		}
		if (second != null) {
			second.sendMessage(SkyPVP.getInstance().PREFIX + "§cDas Team wird in §e10 Sekunden §caufgelöst!");
		}
		startLeaveDelay(player.getName());
	}

	private void askForTeam(Player player, String target) {
		if (player.getName().equalsIgnoreCase(target)) {
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu kannst nicht mit selber teamen!");
			return;
		}
		Player targetPlayer = getPlayerByDisplayName(target);
		if (targetPlayer == null) {
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDieser Spieler ist nicht online!");
			return;
		}
		if (this.getTimedOut().contains(player.getName())) {
			player.sendMessage(SkyPVP.getInstance().PREFIX
					+ "§cDu musst noch warten, bevor du die nächste Teamanfrage versendest!");
			return;
		}
		if (this.getRequests().containsValue(target)) {
			player.sendMessage(SkyPVP.getInstance().PREFIX
					+ "§cDieser Spieler hat bereits eine Anfrage erhalten, versuche es später nochmal!");
			return;
		}
		if (this.getManager().getTeamMate(player) != null) {
			player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu musst zuerst dein Team verlassen!");
			return;
		}
		if (this.getManager().getTeamMate(targetPlayer) != null) {
			player.sendMessage(String.format(SkyPVP.getInstance().PREFIX + "%s §cist bereits in einem Team!",
					targetPlayer.getDisplayName()));
			return;
		}
		this.askRequest(player, targetPlayer);
		player.sendMessage(String.format(
				SkyPVP.getInstance().PREFIX + "§aDu hast dem Spieler %s §aerfolgreich eine Teamanfrage geschickt!",
				targetPlayer.getDisplayName()));
		this.getRequests().put(player.getName(), targetPlayer.getName());
		this.getTimedOut().add(player.getName());
		this.startDelaying(player.getName());
	}

	private void askRequest(Player player, Player targetPlayer) {
		targetPlayer.sendMessage(String.format(SkyPVP.getInstance().PREFIX + "%s §ahat dir eine Teamanfrage geschickt!",
				player.getDisplayName()));
		TextComponent accept = new TextComponent("§a§l§nANNEHMEN");
		accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
				String.format("/frieden accept %s", ChatColor.stripColor(player.getDisplayName()))));
		accept.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,
				new ComponentBuilder("§aNehme die Anfrage von " + player.getDisplayName() + " §aan.").create()));
		TextComponent deny = new TextComponent("§c§l§nABLEHNEN");
		deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
				String.format("/frieden deny %s", ChatColor.stripColor(player.getDisplayName()))));
		deny.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,
				new ComponentBuilder("§cLehne die Anfrage von " + player.getDisplayName() + " §cab.").create()));
		targetPlayer.spigot().sendMessage(new TextComponent(accept, new TextComponent(" §8/ "), deny));
	}

	private void sendInfo(Player player) {
		Player teamMate = this.getManager().getTeamMate(player);
		if (teamMate != null) {
			player.sendMessage(String.format(SkyPVP.getInstance().PREFIX + "§aDu bist mit %s §aim Team!",
					teamMate.getDisplayName()));
			return;
		}
		player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu bist in keinem Team!");
	}

	public TeamManager getManager() {
		return Variables.getTeamManager();
	}

	public Set<String> getTimedOut() {
		return timedOut;
	}

	public void setTimedOut(Set<String> timedOut) {
		this.timedOut = timedOut;
	}

	public Map<String, String> getRequests() {
		return requests;
	}

	public void setRequests(HashMap<String, String> requests) {
		this.requests = requests;
	}

	private Player getPlayerByDisplayName(String displayName) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (ChatColor.stripColor(player.getDisplayName()).equalsIgnoreCase(displayName)) {
				return player;
			}
		}
		return null;
	}

}
