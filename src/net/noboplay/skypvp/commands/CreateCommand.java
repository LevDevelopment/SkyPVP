package net.noboplay.skypvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.Cuboid;
import net.noboplay.skypvp.util.RegionFileManager;

public class CreateCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String commandlabel, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (player.hasPermission("server.admin")) {
				if (SkyPVP.getInstance().pos1.containsKey(player) && SkyPVP.getInstance().pos2.containsKey(player)) {
					SkyPVP.getInstance().region.add(
							new Cuboid(SkyPVP.getInstance().pos1.get(player), SkyPVP.getInstance().pos2.get(player)));
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast die Region erfolgreich erstellt!");
					RegionFileManager.addRegion(SkyPVP.getInstance().pos1.get(player),
							SkyPVP.getInstance().pos2.get(player));
					SkyPVP.getInstance().pos1.remove(player);
					SkyPVP.getInstance().pos2.remove(player);
				} else {
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§cBitte definiere erst die Region!");
				}
			} else {
				player.sendMessage(SkyPVP.getInstance().PREFIX + SkyPVP.getInstance().NOPERM);
			}
		}
		return true;
	}

}
