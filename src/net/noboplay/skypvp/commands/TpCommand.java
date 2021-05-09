package net.noboplay.skypvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.skypvp.SkyPVP;

public class TpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("server.team")) {
				if (args.length == 1) {
					Player target = Bukkit.getPlayer(args[0].trim());
					if (target == null)
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDer Spieler ist nicht online.");
					else {
						if (player.hasPermission("server.admin")) {
							player.teleport(target);
							player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast dich zu "
									+ target.getDisplayName() + " §ateleportiert.");
						} else {
							if (player.getGameMode() == GameMode.SPECTATOR) {
								player.teleport(target);
								player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast dich zu "
										+ target.getDisplayName() + " §ateleportiert.");
							} else
								player.sendMessage(SkyPVP.getInstance().PREFIX
										+ "§cDiese Funktion ist für dich nur im /spectate-Modus verfügbar.");
						}
					}
				} else if (args.length == 2) {
					if (player.hasPermission("server.admin")) {
						Player target = Bukkit.getPlayer(args[0].trim());
						Player targetto = Bukkit.getPlayer(args[1].trim());
						if (target == null) {
							player.sendMessage(
									SkyPVP.getInstance().PREFIX + "§6" + args[0].trim() + " §cist nicht online.");
							return true;
						}
						if (targetto == null) {
							player.sendMessage(
									SkyPVP.getInstance().PREFIX + "§6" + args[1].trim() + " §cist nicht online.");
							return true;
						}
						target.teleport(targetto);
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast " + target.getDisplayName()
								+ " §azu " + targetto.getDisplayName() + " §ateleportiert.");
					} else
						player.sendMessage(SkyPVP.getInstance().PREFIX + SkyPVP.getInstance().NOPERM);
				} else
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§cSyntax: /tp <NAME>");
			} else
				player.sendMessage(SkyPVP.getInstance().PREFIX + SkyPVP.getInstance().NOPERM);
		}
		return true;
	}

}
