package net.noboplay.skypvp.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.KitUtil;
import net.noboplay.skypvp.util.MoneyUtil;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getInventory().getName().equalsIgnoreCase("§3SkyPVP§7-§bKits")) {
			if (event.getCurrentItem() != null || !(event.getCurrentItem().getType() == Material.AIR)) {
				event.setCancelled(true);
				if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Starter")) {
					KitUtil.setRespawnKit(player, 1);
					player.closeInventory();
					KitUtil.giveKit1(player);
					for (PotionEffect ef : player.getActivePotionEffects()) {
						player.removePotionEffect(ef.getType());
					}
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast das §7Starter-Kit §aausgewählt!");
					player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5Enderman")) {
					KitUtil.setRespawnKit(player, 2);
					player.closeInventory();
					KitUtil.giveKit2(player);
					for (PotionEffect ef : player.getActivePotionEffects()) {
						player.removePotionEffect(ef.getType());
					}
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §5Enderman §aausgewählt!");
					player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cBowspammer")) {
					KitUtil.setRespawnKit(player, 3);
					player.closeInventory();
					KitUtil.giveKit3(player);
					for (PotionEffect ef : player.getActivePotionEffects()) {
						player.removePotionEffect(ef.getType());
					}
					player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §cBowspammer §aausgewählt!");
					player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
				} else if (event.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase("§bDiamantenkämpfer")) {
					if (KitUtil.getKit(player, 4)) {
						KitUtil.setRespawnKit(player, 4);
						player.closeInventory();
						KitUtil.giveKit4(player);
						for (PotionEffect ef : player.getActivePotionEffects()) {
							player.removePotionEffect(ef.getType());
						}
						player.sendMessage(
								SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §bDiamantenkämpfer §aausgewählt!");
						player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
					} else {
						if (MoneyUtil.hasEnoughMoney(player.getUniqueId().toString(), 200)) {
							MoneyUtil.removeMoney(player.getUniqueId().toString(), 200);
							KitUtil.addKit(player, 4);
							KitUtil.setRespawnKit(player, 4);
							player.closeInventory();
							KitUtil.giveKit4(player);
							for (PotionEffect ef : player.getActivePotionEffects()) {
								player.removePotionEffect(ef.getType());
							}
							player.sendMessage(SkyPVP.getInstance().PREFIX
									+ "§aDu hast das Kit §bDiamantenkämpfer §agekauft & ausgewählt!");
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 35);
						} else {
							player.closeInventory();
							player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast nicht genügend Geld!");
						}
					}
				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Gold")) {
					if (KitUtil.getKit(player, 5)) {
						KitUtil.setRespawnKit(player, 5);
						player.closeInventory();
						KitUtil.giveKit5(player);
						for (PotionEffect ef : player.getActivePotionEffects()) {
							player.removePotionEffect(ef.getType());
						}
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §6Gold §aausgewählt!");
						player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
					} else {
						if (MoneyUtil.hasEnoughMoney(player.getUniqueId().toString(), 150)) {
							MoneyUtil.removeMoney(player.getUniqueId().toString(), 150);
							KitUtil.addKit(player, 5);
							KitUtil.setRespawnKit(player, 5);
							player.closeInventory();
							KitUtil.giveKit5(player);
							for (PotionEffect ef : player.getActivePotionEffects()) {
								player.removePotionEffect(ef.getType());
							}
							player.sendMessage(
									SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §6Gold §agekauft & ausgewählt!");
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 35);
						} else {
							player.closeInventory();
							player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast nicht genügend Geld!");
						}
					}
				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Speedy")) {
					if (KitUtil.getKit(player, 6)) {
						KitUtil.setRespawnKit(player, 6);
						player.closeInventory();
						KitUtil.giveKit6(player);
						for (PotionEffect ef : player.getActivePotionEffects()) {
							player.removePotionEffect(ef.getType());
						}
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0));
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 0));
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §9Speedy §aausgewählt!");
						player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
					} else {
						if (MoneyUtil.hasEnoughMoney(player.getUniqueId().toString(), 200)) {
							MoneyUtil.removeMoney(player.getUniqueId().toString(), 200);
							KitUtil.setRespawnKit(player, 6);
							KitUtil.addKit(player, 6);
							player.closeInventory();
							KitUtil.giveKit6(player);
							for (PotionEffect ef : player.getActivePotionEffects()) {
								player.removePotionEffect(ef.getType());
							}
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0));
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 0));
							player.sendMessage(
									SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §9Speedy §agekauft & ausgewählt!");
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 35);
						} else {
							player.closeInventory();
							player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast nicht genügend Geld!");
						}
					}
				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Aura")) {
					if (KitUtil.getKit(player, 7)) {
						KitUtil.setRespawnKit(player, 7);
						player.closeInventory();
						for (PotionEffect ef : player.getActivePotionEffects()) {
							player.removePotionEffect(ef.getType());
						}
						KitUtil.giveKit7(player);
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §9Aura §aausgewählt!");
						player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
					} else {
						if (MoneyUtil.hasEnoughMoney(player.getUniqueId().toString(), 600)) {
							MoneyUtil.removeMoney(player.getUniqueId().toString(), 600);
							KitUtil.setRespawnKit(player, 7);
							KitUtil.addKit(player, 7);
							player.closeInventory();
							for (PotionEffect ef : player.getActivePotionEffects()) {
								player.removePotionEffect(ef.getType());
							}
							KitUtil.giveKit7(player);
							player.sendMessage(
									SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §9Aura §agekauft & ausgewählt!");
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 35);
						} else {
							player.closeInventory();
							player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast nicht genügend Geld!");
						}
					}
				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cCuzImRodPvP")) {
					if (KitUtil.getKit(player, 9)) {
						KitUtil.setRespawnKit(player, 9);
						player.closeInventory();
						KitUtil.giveKit9(player);
						for (PotionEffect ef : player.getActivePotionEffects()) {
							player.removePotionEffect(ef.getType());
						}
						player.sendMessage(
								SkyPVP.getInstance().PREFIX + "§aDu hast das §cCuzImRodPvP-Kit §aausgewählt!");
						player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
					} else {
						if (MoneyUtil.hasEnoughMoney(player.getUniqueId().toString(), 500)) {
							MoneyUtil.removeMoney(player.getUniqueId().toString(), 500);
							KitUtil.setRespawnKit(player, 9);
							KitUtil.addKit(player, 9);
							player.closeInventory();
							KitUtil.giveKit9(player);
							for (PotionEffect ef : player.getActivePotionEffects()) {
								player.removePotionEffect(ef.getType());
							}
							player.sendMessage(SkyPVP.getInstance().PREFIX
									+ "§aDu hast das §cCuzImRodPvP-Kit §agekauft & ausgewählt!");
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 35);
							return;
						} else {
							player.closeInventory();
							player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast nicht genügend Geld!");
						}
					}
				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Tank")) {
					if (KitUtil.getKit(player, 8)) {
						KitUtil.setRespawnKit(player, 8);
						player.closeInventory();
						KitUtil.giveKit8(player);
						for (PotionEffect ef : player.getActivePotionEffects()) {
							player.removePotionEffect(ef.getType());
						}
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 0));
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §3Tank §aausgewählt!");
						player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
						return;
					} else {
						if (MoneyUtil.hasEnoughMoney(player.getUniqueId().toString(), 800)) {
							MoneyUtil.removeMoney(player.getUniqueId().toString(), 800);
							KitUtil.setRespawnKit(player, 8);
							player.closeInventory();
							KitUtil.giveKit8(player);
							KitUtil.addKit(player, 8);
							for (PotionEffect ef : player.getActivePotionEffects()) {
								player.removePotionEffect(ef.getType());
							}
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 0));
							player.sendMessage(
									SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §3Tank §agekauft & ausgewählt!");
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 35);
							return;
						} else {
							player.closeInventory();
							player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast nicht genügend Geld!");
						}
					}
				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aTracker")) {
					if (KitUtil.getKit(player, 10)) {
						KitUtil.setRespawnKit(player, 10);
						player.closeInventory();
						KitUtil.giveKit10(player);
						for (PotionEffect ef : player.getActivePotionEffects()) {
							player.removePotionEffect(ef.getType());
						}
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §aTracker §aausgewählt!");
						player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
					} else {
						if (MoneyUtil.hasEnoughMoney(player.getUniqueId().toString(), 400)) {
							MoneyUtil.removeMoney(player.getUniqueId().toString(), 400);
							KitUtil.setRespawnKit(player, 10);
							player.closeInventory();
							KitUtil.giveKit10(player);
							KitUtil.addKit(player, 10);
							for (PotionEffect ef : player.getActivePotionEffects()) {
								player.removePotionEffect(ef.getType());
							}
							player.sendMessage(SkyPVP.getInstance().PREFIX
									+ "§aDu hast das Kit §aTracker §agekauft & ausgewählt!");
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 35);
						} else {
							player.closeInventory();
							player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast nicht genügend Geld!");
						}
					}
				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eArchitekt")) {
					if (KitUtil.getKit(player, 11)) {
						KitUtil.setRespawnKit(player, 11);
						player.closeInventory();
						KitUtil.giveKit11(player);
						for (PotionEffect ef : player.getActivePotionEffects()) {
							player.removePotionEffect(ef.getType());
						}
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §eArchitekt §aausgewählt!");
						player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
					} else {
						if (MoneyUtil.hasEnoughMoney(player.getUniqueId().toString(), 300)) {
							MoneyUtil.removeMoney(player.getUniqueId().toString(), 300);
							KitUtil.setRespawnKit(player, 11);
							player.closeInventory();
							KitUtil.giveKit11(player);
							KitUtil.addKit(player, 11);
							for (PotionEffect ef : player.getActivePotionEffects()) {
								player.removePotionEffect(ef.getType());
							}
							player.sendMessage(SkyPVP.getInstance().PREFIX
									+ "§aDu hast das Kit §eArchitekt §agekauft & ausgewählt!");
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 35);
						} else {
							player.closeInventory();
							player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast nicht genügend Geld!");
						}
					}
				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Hexe")) {
					if (KitUtil.getKit(player, 12)) {
						KitUtil.setRespawnKit(player, 12);
						player.closeInventory();
						KitUtil.giveKit12(player);
						for (PotionEffect ef : player.getActivePotionEffects()) {
							player.removePotionEffect(ef.getType());
						}
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §2Hexe §aausgewählt!");
						player.playSound(player.getLocation(), Sound.CLICK, 10.0F, 1.0F);
					} else {
						if (MoneyUtil.hasEnoughMoney(player.getUniqueId().toString(), 1000)) {
							MoneyUtil.removeMoney(player.getUniqueId().toString(), 1000);
							KitUtil.setRespawnKit(player, 12);
							player.closeInventory();
							KitUtil.giveKit12(player);
							KitUtil.addKit(player, 12);
							for (PotionEffect ef : player.getActivePotionEffects()) {
								player.removePotionEffect(ef.getType());
							}
							player.sendMessage(
									SkyPVP.getInstance().PREFIX + "§aDu hast das Kit §2Hexe §agekauft & ausgewählt!");
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 35);
						} else {
							player.closeInventory();
							player.sendMessage(SkyPVP.getInstance().PREFIX + "§cDu hast nicht genügend Geld!");
						}
					}
				}
				player.updateInventory();
			}
		}
	}

}
