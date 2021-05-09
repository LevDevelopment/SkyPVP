package net.noboplay.skypvp.listener;

import net.noboplay.core.api.ActionBar;
import net.noboplay.core.util.database.Databases;
import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.MoneyUtil;
import net.noboplay.skypvp.util.ScoreboardUtil;
import net.noboplay.skypvp.util.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        Player killer = player.getKiller();
        if (killer != null) {
            killer.removePotionEffect(PotionEffectType.REGENERATION);
            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 4));
            killer.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 60, 4));
        }
        event.setDeathMessage(null);
        if (SkyPVP.getInstance().logtask.containsKey(player.getName())) {
            SkyPVP.getInstance().logtask.get(player.getName()).cancel();
            SkyPVP.getInstance().logtask.get(player.getName()).run();
        }
        Bukkit.getScheduler().runTaskAsynchronously(SkyPVP.getInstance(), new Runnable() {

            @Override
            public void run() {
                Player killer = player.getKiller();
                StatsUtil.addDeaths(player.getUniqueId().toString(), 1);
                SkyPVP.getInstance().killstreak.put(player.getName(), 0.0);
                if (killer != null && !killer.getName().equalsIgnoreCase(player.getName())) {
                    player.sendMessage(SkyPVP.getInstance().PREFIX + "§6Leben von " + killer.getDisplayName() + ": "
                            + getHealth(killer));
                    ActionBar.sendActionBar(killer, "§e+§a5 §3Nobos");
                    killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 1, 50);
                    MoneyUtil.addMoney(killer.getUniqueId().toString(), 5);
                    StatsUtil.addKills(killer.getUniqueId().toString(), 1);
                    ScoreboardUtil.updateScoreboard(killer);
                    SkyPVP.getInstance().killstreak.put(player.getName(), 0.0);
                    if (SkyPVP.getInstance().killstreak.get(killer.getName()) == null) {
                        SkyPVP.getInstance().killstreak.put(killer.getName(), 1.0);
                    } else {
                        SkyPVP.getInstance().killstreak.put(killer.getName(),
                                SkyPVP.getInstance().killstreak.get(killer.getName()) + 1.0);
                    }
                    if ((SkyPVP.getInstance().killstreak.get(killer.getName()) / 5) == Math
                            .floor(SkyPVP.getInstance().killstreak.get(killer.getName()) / 5)
                            && !Double.isInfinite(SkyPVP.getInstance().killstreak.get(killer.getName()) / 5)
                            && SkyPVP.getInstance().killstreak.get(killer.getName()).intValue() != 0) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            ActionBar.sendActionBar(all,
                                    "§b" + killer.getDisplayName() + " §ahat eine §b"
                                            + SkyPVP.getInstance().killstreak.get(killer.getName()).intValue()
                                            + "er §aKillstreak erreicht!");
                            all.playSound(all.getLocation(), Sound.FIREWORK_TWINKLE2, 1, 1);
                        }
                        giveSpeed(killer, SkyPVP.getInstance().killstreak.get(killer.getName()).intValue() * 20);
                        Databases.getTokensDatabase().addTokens(killer.getUniqueId().toString(),
                                SkyPVP.getInstance().killstreak.get(killer.getName()).intValue());
                        killer.sendMessage("§bNoboPlay§ §8» §aDu hast §b"
                                + SkyPVP.getInstance().killstreak.get(killer.getName()).intValue()
                                + " §aTokens bekommen!");
                        if (!(killer.getInventory().firstEmpty() == -1)) {
                            killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                        }
                    }
                }
            }
        });
        Bukkit.getScheduler().runTaskLater(SkyPVP.getInstance(), new Runnable() {

            @Override
            public void run() {
                player.spigot().respawn();
            }

        }, 5);
    }

    public static String getHealth(Player player) {
        StringBuilder builder = new StringBuilder();
        double health = player.getHealth() / 2;
        int loop = (int) health;

        for (int i = 0; i < loop; i++) {
            builder.append("§c❤");
        }
        if (health >= 0.5 && loop != 10) {
            builder.append("§c❥");
        }
        for (int i = 0; i < 9 - loop; i++) {
            builder.append("§f❤");
        }
        return builder.toString();
    }

    public void giveSpeed(final Player player, final int dauer) {
        Bukkit.getScheduler().runTask(SkyPVP.getInstance(), new Runnable() {

            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, dauer, 0));
            }
        });
    }
}
