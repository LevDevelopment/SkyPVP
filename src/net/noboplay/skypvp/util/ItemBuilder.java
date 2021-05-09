package net.noboplay.skypvp.util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

	public static ItemStack createItem(String name, String lore, Material material, int amount, int subid) {
		ItemStack is = new ItemStack(material, amount, (short) subid);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList(lore));
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack createItem(String name, List<String> lore, Material material, int amount, int subid) {
		ItemStack is = new ItemStack(material, amount, (short) subid);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static void enchantItem(ItemStack itemstack, Enchantment enchantment, int enchantmentlevel) {
		itemstack.addUnsafeEnchantment(enchantment, enchantmentlevel);
	}

	public static void setUnbreakable(ItemStack itemstack, boolean bool) {
		ItemMeta meta = itemstack.getItemMeta();
		meta.spigot().setUnbreakable(bool);
		itemstack.setItemMeta(meta);
	}

	public static ItemStack createHead(String name, String lore, String owner) {
		ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta im = (SkullMeta) is.getItemMeta();
		im.setOwner(owner);
		im.setDisplayName(name);
		im.setLore(Arrays.asList(lore));
		is.setItemMeta(im);

		return is;
	}

	public static void addItemFlag(ItemStack itemstack, ItemFlag itemflag) {
		ItemMeta meta = itemstack.getItemMeta();
		meta.addItemFlags(itemflag);
		itemstack.setItemMeta(meta);
	}

	public static ItemStack createBoots(String name, String lore, int amount, Color color) {
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, amount);
		LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		meta.setColor(color);
		boots.setItemMeta(meta);
		return boots;
	}

}
