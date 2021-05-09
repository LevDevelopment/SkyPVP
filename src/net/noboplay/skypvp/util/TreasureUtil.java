package net.noboplay.skypvp.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.noboplay.core.api.ActionBar;
import net.noboplay.skypvp.SkyPVP;

public class TreasureUtil {

	public static HashMap<Block, Inventory> chests = new HashMap<Block, Inventory>();
	public static ArrayList<ItemStack> items = new ArrayList<ItemStack>();

	public static void startThread() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(SkyPVP.getInstance(), new Runnable() {

			@Override
			public void run() {
				chests.clear();
				for (Player all : Bukkit.getOnlinePlayers()) {
					ActionBar.sendActionBar(all, "§cDie Treasures wurden refilled!");
				}
				SpawnUtil.getSpawn().getWorld().strikeLightningEffect(SpawnUtil.getSpawn());
			}
		}, 0, 20 * 60 * 5);
	}

	public static void fillItemList() {
		items.add(new ItemStack(Material.APPLE, 2));
		items.add(new ItemStack(Material.BAKED_POTATO, 3));
		items.add(new ItemStack(Material.BREAD, 2));
		items.add(new ItemStack(Material.CARROT_ITEM, 2));
		items.add(new ItemStack(Material.COOKED_BEEF, 2));
		items.add(new ItemStack(Material.COOKED_CHICKEN, 2));
		items.add(new ItemStack(Material.COOKED_FISH, 2));
		items.add(new ItemStack(Material.COOKIE, 2));
		items.add(new ItemStack(Material.MELON, 3));
		items.add(new ItemStack(Material.RAW_BEEF, 4));
		items.add(new ItemStack(Material.RAW_CHICKEN, 2));
		items.add(new ItemStack(Material.RAW_FISH, 3));
		items.add(new ItemStack(Material.PORK, 2));
		items.add(new ItemStack(Material.GRILLED_PORK, 2));
		items.add(new ItemStack(Material.CHAINMAIL_BOOTS));
		items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		items.add(new ItemStack(Material.CHAINMAIL_HELMET));
		items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		items.add(new ItemStack(Material.IRON_BOOTS));
		items.add(new ItemStack(Material.IRON_CHESTPLATE));
		items.add(new ItemStack(Material.IRON_HELMET));
		items.add(new ItemStack(Material.IRON_LEGGINGS));
		items.add(new ItemStack(Material.GOLD_BOOTS));
		items.add(new ItemStack(Material.GOLD_CHESTPLATE));
		items.add(new ItemStack(Material.GOLD_HELMET));
		items.add(new ItemStack(Material.GOLD_LEGGINGS));
		items.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		items.add(new ItemStack(Material.WOOD_SWORD));
		items.add(new ItemStack(Material.STONE_SWORD));
		items.add(new ItemStack(Material.DIAMOND_AXE));
		items.add(new ItemStack(Material.FISHING_ROD));
		items.add(new ItemStack(Material.EXP_BOTTLE, 2));
		items.add(new ItemStack(Material.ARROW, 5));
		items.add(new ItemStack(Material.ENDER_PEARL, 2));
	}

}
