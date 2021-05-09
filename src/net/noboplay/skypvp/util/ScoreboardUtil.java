package net.noboplay.skypvp.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import net.noboplay.skypvp.SkyPVP;

public class ScoreboardUtil {

	public static void updateScoreboard(final Player player) {
		Bukkit.getScheduler().runTask(SkyPVP.getInstance(), new Runnable() {

			@Override
			public void run() {
				Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
				Objective obj = board.registerNewObjective("SkyPVP", "bbb");

				obj.setDisplayName("§8» §3SkyPVP");
				obj.setDisplaySlot(DisplaySlot.SIDEBAR);

				double deaths = StatsUtil.getDeaths(player.getUniqueId().toString());
				double kills = StatsUtil.getKills(player.getUniqueId().toString());
				double kd;
				if (StatsUtil.getDeaths(player.getUniqueId().toString()) == 0) {
					kd = kills;
				} else {
					kd = kills / deaths;
					kd *= 100.0D;
					kd = Math.round(kd);
					kd /= 100.0D;
				}
				Score ten = obj.getScore("§eKills:");
				Score nine = obj.getScore("§3" + (int) kills + " §e");
				Score eight = obj.getScore("§7 ");
				Score seven = obj.getScore("§eDeaths:");
				Score six = obj.getScore("§3" + (int) deaths + " §r");
				Score five = obj.getScore("§3 ");
				Score four = obj.getScore("§eK/D:");
				Score three = obj.getScore("§3" + Double.valueOf(kd));
				Score two = obj.getScore("§r ");
				Score one = obj.getScore("§eNobos:");
				Score zero = obj.getScore("§3" + MoneyUtil.getMoney(player.getUniqueId().toString()) + " ");

				ten.setScore(10);
				nine.setScore(9);
				eight.setScore(8);
				seven.setScore(7);
				six.setScore(6);
				five.setScore(5);
				four.setScore(4);
				three.setScore(3);
				two.setScore(2);
				one.setScore(1);
				zero.setScore(0);

				player.setScoreboard(board);
			}
		});
	}
}
