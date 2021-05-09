package net.noboplay.skypvp.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.noboplay.core.api.ActionBar;
import net.noboplay.skypvp.SkyPVP;
import net.noboplay.skypvp.util.RegionUtil;
import net.noboplay.skypvp.util.SpawnUtil;
import net.noboplay.skypvp.util.TreasureUtil;
import net.noboplay.skypvp.util.Variables;

public class PlayerInteractListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock() != null) {
				if (event.getClickedBlock().getType() == Material.CHEST) {
					if (SkyPVP.getInstance().build.contains(player.getName())) {
						return;
					}
					event.setCancelled(true);
					if (TreasureUtil.chests.containsKey(event.getClickedBlock())) {
						player.openInventory(TreasureUtil.chests.get(event.getClickedBlock()));
						player.sendMessage(
								SkyPVP.getInstance().PREFIX + "§cDas Treasure wurde bereits schon geöffnet!");
						player.sendMessage(SkyPVP.getInstance().PREFIX + "§cEs wird bald refilled.");
					} else {
						Inventory inventory = Bukkit.createInventory(null, 27, "Treasure");
						fillChest(inventory);
						player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 45);
						TreasureUtil.chests.put(event.getClickedBlock(), inventory);
						player.openInventory(inventory);
					}
				} else if (event.getClickedBlock().getState() instanceof Sign) {
					Sign sign = (Sign) event.getClickedBlock().getState();
					int id = 0;
					int subid = 0;
					String string = "";
					ItemStack item = null;
					if (sign.getLine(0).equalsIgnoreCase("§7[§3SkyPVP§7]")
							|| sign.getLine(0).equalsIgnoreCase("§3SkyPvP")) {
						if (sign.getLine(1).contains(":")) {
							String[] array = sign.getLine(1).split(":");
							try {
								id = Integer.parseInt(array[0]);
								subid = Integer.parseInt(array[1]);
							} catch (Exception ex) {
								player.sendMessage(SkyPVP.getInstance().PREFIX
										+ "§cDas Schild ist defekt, bitte kontaktiere einen Admin mit folgenden Informationen:");
								player.sendMessage("§bSignLoc: X:" + player.getLocation().getBlockX() + " Y:"
										+ player.getLocation().getBlockY() + " Z:" + player.getLocation().getBlockZ());
								return;
							}
						} else {
							try {
								id = Integer.parseInt(sign.getLine(1));
							} catch (Exception ex) {
								player.sendMessage(SkyPVP.getInstance().PREFIX
										+ "§cDas Schild ist defekt, bitte kontaktiere einen Admin mit folgenden Informationen:");
								player.sendMessage("§bSignLoc: X:" + player.getLocation().getBlockX() + " Y:"
										+ player.getLocation().getBlockY() + " Z:" + player.getLocation().getBlockZ());
								return;
							}
						}
						if (id == 384) {
							if (!SkyPVP.getInstance().exp.asMap().containsKey(player.getName())) {
								player.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
								SpawnUtil.tpSpawn(player);
								player.sendMessage(
										SkyPVP.getInstance().PREFIX + "§aFür XP-Flaschen brauchst du mehr Geduld!");
								SkyPVP.getInstance().exp.put(player.getName(), player.getName());
							} else
								player.sendMessage(SkyPVP.getInstance().PREFIX
										+ "§cDu musst jetzt noch ein bisschen länger warten...");
							return;
						}
						string = "§bFür dich: " + id + (subid > 0 ? subid : "");
						item = new ItemStack(Material.getMaterial(id));
						item.setDurability((short) subid);
						// Inventory inventory = Bukkit.createInventory(null,
						// 45, string);
						// for (int i = 0; i < 45; i++) {
						// inventory.setItem(i, item);
						// }
						Inventory inventory = Bukkit.createInventory(null, 9, string);
						inventory.setItem(4, item);
						player.openInventory(inventory);
					}
				}
			}
		} else if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
			if (event.getItem().getType() == Material.COMPASS) {
				Player target = getNearest(player);
				if (target == null) {
					ActionBar.sendActionBar(player, "§cEs ist kein Spieler in deiner Nähe!");
					return;
				}
				int blocks = (int) player.getLocation().distance(target.getLocation());
				ActionBar.sendActionBar(player, target.getDisplayName() + " §8» §aEntfernung: §b" + blocks
						+ (blocks == 1 ? " Block" : " Blöcke"));
			}
		} else if (event.getAction() == Action.PHYSICAL) {
			if (event.getClickedBlock().getType() == Material.GOLD_PLATE) {
				event.setCancelled(true);
				player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1, 1);
				player.setAllowFlight(true);
				player.setVelocity(player.getLocation().getDirection().multiply(3.5).setY(4));
				player.setAllowFlight(false);
			}
		}
	}

	private void fillChest(Inventory inv) {
		for (int i = 0; i < Variables.getRandom().nextInt(10 - 4 + 1) + 4; i++) {
			inv.setItem(Variables.getRandom().nextInt(inv.getSize() - 1),
					TreasureUtil.items.get(Variables.getRandom().nextInt(TreasureUtil.items.size() - 1)));
		}
	}

	public Player getNearest(Player p) {
		double distance = Double.MAX_VALUE;
		Player target = null;

		for (Entity entity : p.getNearbyEntities(500, 500, 500)) {
			if (entity instanceof Player) {
				double dis = p.getLocation().distance(entity.getLocation());
				if (dis < distance) {
					distance = dis;
					target = (Player) entity;
					if (RegionUtil.isInRegion(target) || SkyPVP.getInstance().spectate.contains(target.getName())) {
						continue;
					}
				}
			}
		}
		return target;
	}

}
