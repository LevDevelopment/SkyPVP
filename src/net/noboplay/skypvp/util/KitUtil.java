package net.noboplay.skypvp.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitUtil {

	public static HashMap<String, Integer> respawn = new HashMap<String, Integer>();

	public static void openKitInventory(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 36, "§3SkyPVP§7-§bKits");
		ItemStack starter = ItemBuilder.createItem("§7Starter",
				Arrays.asList("§aStartet mit einem Eisenschwert & Kettenrüstung", "§aGratis"), Material.IRON_SWORD, 1,
				0);
		ItemBuilder.addItemFlag(starter, ItemFlag.HIDE_ATTRIBUTES);
		ItemStack enderman = ItemBuilder.createItem("§5Enderman",
				Arrays.asList("§5Startet mit 32 Enderperlen", "§aGratis"), Material.ENDER_PEARL, 1, 0);
		ItemStack bowspammer = ItemBuilder.createItem("§cBowspammer",
				Arrays.asList("§cStartet mit 64 Pfeilen", "§aGratis"), Material.BOW, 1, 0);
		ItemStack diamond = ItemBuilder.createItem("§bDiamantenkämpfer",
				Arrays.asList("§bStartet mit einem Diamantenschwert & Goldrüstung",
						(getKit(player, 4) ? "§aGekauft" : "§c200 Nobos")),
				Material.DIAMOND_SWORD, 1, 0);
		ItemBuilder.addItemFlag(diamond, ItemFlag.HIDE_ATTRIBUTES);
		ItemStack gold = ItemBuilder.createItem("§6Gold", Arrays.asList("§6Alles aus Gold: Rüstung, Apfel & Schwert",
				(getKit(player, 5) ? "§aGekauft" : "§c150 Nobos")), Material.GOLD_INGOT, 1, 0);
		ItemStack speed = ItemBuilder.createItem("§9Speedy", Arrays.asList("§9Bekommt Geschwindigkeit & Sprungkraft",
				(getKit(player, 6) ? "§aGekauft" : "§c200 Nobos")), Material.POTION, 1, 8194);
		ItemStack aura = ItemBuilder.createItem("§9Aura",
				Arrays.asList("§9Mit Aura-Stick", (getKit(player, 7) ? "§aGekauft" : "§c600 Nobos")), Material.STICK, 1,
				0);
		ItemBuilder.enchantItem(aura, Enchantment.DURABILITY, 1);
		ItemBuilder.addItemFlag(aura, ItemFlag.HIDE_ENCHANTS);
		ItemStack rod = ItemBuilder.createItem("§cCuzImRodPvP",
				Arrays.asList("§cSei ein Hive-Player", (getKit(player, 9) ? "§aGekauft" : "§c500 Nobos")),
				Material.FISHING_ROD, 1, 0);
		ItemStack tank = ItemBuilder.createItem("§3Tank", Arrays.asList("§3Bekommt eine Eisenrüstung & Langsamkeit",
				(getKit(player, 8) ? "§aGekauft" : "§c800 Nobos")), Material.IRON_CHESTPLATE, 1, 0);
		ItemStack tracker = ItemBuilder.createItem("§aTracker",
				Arrays.asList("§aBekommt einen Spielertracker", (getKit(player, 10) ? "§aGekauft" : "§c400 Nobos")),
				Material.COMPASS, 1, 0);
		ItemBuilder.enchantItem(tracker, Enchantment.DURABILITY, 1);
		ItemBuilder.addItemFlag(tracker, ItemFlag.HIDE_ENCHANTS);
		ItemStack builder = ItemBuilder.createItem("§eArchitekt",
				Arrays.asList("§eStartet mit einer Axt & einem Regenerationstrank",
						(getKit(player, 11) ? "§aGekauft" : "§c300 Nobos")),
				Material.DIAMOND_AXE, 1, 0);
		ItemBuilder.addItemFlag(builder, ItemFlag.HIDE_ATTRIBUTES);
		ItemStack hexe = ItemBuilder.createItem("§2Hexe",
				Arrays.asList("§2Startet mit Schadens-, Langsamkeits- & Heilungstränken",
						(getKit(player, 12) ? "§aGekauft" : "§c1000 Nobos")),
				Material.POTION, 1, 16460);
		ItemBuilder.addItemFlag(hexe, ItemFlag.HIDE_POTION_EFFECTS);

		inventory.setItem(0, starter);
		inventory.setItem(4, enderman);
		inventory.setItem(8, bowspammer);
		inventory.setItem(9, diamond);
		inventory.setItem(13, gold);
		inventory.setItem(17, speed);
		inventory.setItem(18, aura);
		inventory.setItem(22, rod);
		inventory.setItem(26, tank);
		inventory.setItem(27, tracker);
		inventory.setItem(31, builder);
		inventory.setItem(35, hexe);

		player.openInventory(inventory);
	}

	public static boolean playerExsits(String uuid) {
		try {
			PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * FROM Kits WHERE UUID=?");
			statement.setString(1, uuid);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getString("UUID") != null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void createPlayer(String uuid) {
		if (!(playerExsits(uuid))) {
			try {
				PreparedStatement statement = MySQL.getConnection().prepareStatement(
						"INSERT INTO Kits(UUID, kit4, kit5, kit6, kit7, kit8, kit9, kit10, kit11, kit12) VALUES (?, false, false, false, false, false, false, false, false, false);");
				statement.setString(1, uuid);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean getKit(Player p, int kit) {
		if (playerExsits(p.getUniqueId().toString())) {
			try {
				PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * FROM Kits WHERE UUID=?");
				statement.setString(1, p.getUniqueId().toString());
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					return rs.getBoolean("kit" + kit);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(p.getUniqueId().toString());
			getKit(p, kit);
		}
		return false;
	}

	public static void addKit(Player p, int kit) {
		if (playerExsits(p.getUniqueId().toString())) {
			try {
				PreparedStatement statement = MySQL.getConnection()
						.prepareStatement("UPDATE Kits SET kit?=1 WHERE UUID=?;");
				statement.setInt(1, kit);
				statement.setString(2, p.getUniqueId().toString());
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(p.getUniqueId().toString());
			addKit(p, kit);
		}
	}

	public static void setRespawnKit(Player p, int kit) {
		respawn.put(p.getName(), kit);
	}

	public static int getRespawnKit(Player p) {
		return respawn.get(p.getName());
	}

	public static void giveKit1(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		p.getInventory().addItem(new ItemStack(Material.BOW));
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.getInventory().setItem(8, new ItemStack(Material.ARROW, 10));
		p.updateInventory();
	}

	public static void giveKit2(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
		p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
		p.getInventory().addItem(new ItemStack(Material.BOW));
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 32));
		p.getInventory().setItem(8, new ItemStack(Material.ARROW, 10));
		p.updateInventory();
	}

	public static void giveKit3(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
		p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
		p.getInventory().addItem(new ItemStack(Material.BOW));
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.getInventory().setItem(8, new ItemStack(Material.ARROW, 64));
		p.updateInventory();
	}

	public static void giveKit4(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
		p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		p.getInventory().addItem(new ItemStack(Material.BOW));
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.getInventory().setItem(8, new ItemStack(Material.ARROW, 5));
		p.updateInventory();
	}

	public static void giveKit5(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
		p.getInventory().addItem(new ItemStack(Material.GOLD_SWORD));
		p.getInventory().addItem(new ItemStack(Material.BOW));
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
		p.getInventory().setItem(8, new ItemStack(Material.ARROW, 5));
		p.updateInventory();
	}

	public static void giveKit6(final Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
		p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		p.getInventory().addItem(new ItemStack(Material.BOW));
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.getInventory().setItem(8, new ItemStack(Material.ARROW, 10));
		p.updateInventory();
	}

	public static void giveKit7(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		ItemStack item2 = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		item2.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		p.getInventory().setChestplate(item2);
		p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		ItemStack item = new ItemStack(Material.STICK, 1);
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
		item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		ItemMeta itemname = item.getItemMeta();
		itemname.setDisplayName("§bKnüppel");
		item.setItemMeta(itemname);
		p.getInventory().addItem(item);
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 3));
		p.updateInventory();
	}

	public static void giveKit9(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.updateInventory();
	}

	public static void giveKit8(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		ItemStack item = new ItemStack(Material.WOOD_SWORD);
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
		p.getInventory().addItem(item);
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.updateInventory();
	}

	public static void giveKit10(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		p.getInventory().addItem(new ItemStack(Material.BOW));
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassm = compass.getItemMeta();
		compassm.setDisplayName("§bTracker");
		compassm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		compass.setItemMeta(compassm);
		compass.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		p.getInventory().setItem(2, compass);
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.getInventory().setItem(8, new ItemStack(Material.ARROW, 10));
		p.updateInventory();
	}

	public static void giveKit11(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		p.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE));
		p.getInventory().addItem(new ItemStack(Material.BOW));
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 8257));
		p.getInventory().setItem(8, new ItemStack(Material.ARROW, 10));
		p.updateInventory();
	}

	public static void giveKit12(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
		p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 10));
		p.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16428));
		p.getInventory().addItem(new ItemStack(Material.POTION, 2, (short) 16426));
		p.getInventory().addItem(new ItemStack(Material.POTION, 2, (short) 16453));
		p.updateInventory();
	}

}
