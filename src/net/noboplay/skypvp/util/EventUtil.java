package net.noboplay.skypvp.util;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.noboplay.core.api.ActionBar;
import net.noboplay.core.api.Title;
import net.noboplay.skypvp.SkyPVP;

public class EventUtil {

	public static int Eventtime = 0;
	public static int taskid;
	public static boolean event;
	static ArrayList<ItemStack> items = new ArrayList<ItemStack>();

	public static void startEvent() {
		if (event) {
			Bukkit.broadcastMessage(SkyPVP.getInstance().PREFIX + "§cDas Event ist vorbei!");
			Bukkit.getScheduler().cancelTask(taskid);
			event = false;
			return;
		}
		SpawnUtil.getSpawn().getWorld().strikeLightningEffect(SpawnUtil.getSpawn());
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.playSound(all.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
			Title.sendTitle(all, "", "§aHerzlich Willkommen beim NoboPlay.net-SkyPVP-Event!");
		}
		event = true;
		taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyPVP.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (Variables.getRandom().nextInt(3) == 2) {
					startXPSpawning();
				} else {
					spawnChest();
				}
			}
		}, 0, 20 * 60 * 1);
	}

	public static void spawnChest() {
		final Location location = new Location(SpawnUtil.getSpawn().getWorld(), 0, 0, 0);

		location.setX(SpawnUtil.getSpawn().getX() + Math.random() * 40 * 2 - 40);
		location.setZ(SpawnUtil.getSpawn().getZ() + Math.random() * 50 * 2 - 50);

		location.setY(26 - Math.random());
		if (!(location.getBlock().getType() == Material.AIR) || !getFree(location.clone().add(0, 3, 0))) {
			spawnChest();
			return;
		}
		location.getBlock().setType(Material.TRAPPED_CHEST);
		Block block = location.getBlock();
		Chest chest = (Chest) block.getState();
		Inventory inventory = chest.getBlockInventory();
		fillChest(inventory);
		location.getWorld().strikeLightningEffect(location);
		createBeacon(location.clone().add(0, 3, 0));
		for (Player all : Bukkit.getOnlinePlayers()) {
			ActionBar.sendActionBar(all, "§aEine §bEvent-Chest §aist gespawnt!");
		}
		Bukkit.getScheduler().runTaskLater(SkyPVP.getInstance(), new Runnable() {

			@Override
			public void run() {
				Block block = location.getBlock();
				Chest chest = (Chest) block.getState();
				chest.getBlockInventory().clear();
				location.getBlock().setType(Material.AIR);
				removeBeacon(location.clone().add(0, 3, 0));
			}
		}, 20 * 30);
	}

	private static void fillChest(Inventory inv) {
		for (int i = 0; i < Variables.getRandom().nextInt(5 - 3 + 1) + 3; i++) {
			inv.setItem(Variables.getRandom().nextInt(inv.getSize() - 1),
					items.get(Variables.getRandom().nextInt(items.size() - 1)));
		}
	}

	public static void fillItemList() {
		items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		items.add(new ItemStack(Material.CHAINMAIL_HELMET));
		items.add(new ItemStack(Material.IRON_BOOTS));
		items.add(new ItemStack(Material.IRON_CHESTPLATE));
		items.add(new ItemStack(Material.IRON_HELMET));
		items.add(new ItemStack(Material.IRON_LEGGINGS));
		items.add(new ItemStack(Material.DIAMOND_HELMET));
		items.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		items.add(new ItemStack(Material.DIAMOND_LEGGINGS));
		items.add(new ItemStack(Material.DIAMOND_BOOTS));
		items.add(new ItemStack(Material.EXP_BOTTLE, 16));
		items.add(new ItemStack(Material.DIAMOND_SWORD));
		items.add(new ItemStack(Material.ARROW, 15));
		items.add(new ItemStack(Material.BOW));
		items.add(new ItemStack(Material.FISHING_ROD));
		items.add(new ItemStack(Material.ENDER_PEARL, 16));
	}

	public static void spawnBottle(Location loc, Float yaw) {
		final ThrownExpBottle teb = (ThrownExpBottle) loc.getWorld().spawnEntity(loc, EntityType.THROWN_EXP_BOTTLE);
		loc.setPitch(0);
		loc.setYaw(yaw);
		Vector v = loc.getDirection().multiply(0.2).setY(0.7);
		teb.setVelocity(v);
	}

	public static void foutain(Location loc) {
		for (int i = -209; i < 179; i = i + 30) {
			spawnBottle(loc, (float) i);
		}
	}

	public static void startXPSpawning() {
		for (Player all : Bukkit.getOnlinePlayers()) {
			ActionBar.sendActionBar(all, "§aXP §b4 free §c<3");
		}
		final AtomicInteger i = new AtomicInteger(0);
		final Location location = new Location(SpawnUtil.getSpawn().getWorld(), 0, 0, 0);
		location.setX(SpawnUtil.getSpawn().getX() + Math.random() * 40 * 2 - 40);
		location.setZ(SpawnUtil.getSpawn().getZ() + Math.random() * 50 * 2 - 50);
		location.setY(26 - Math.random());
		SpawnUtil.getSpawn().getWorld().strikeLightningEffect(location);
		new BukkitRunnable() {

			@Override
			public void run() {
				foutain(location);
				if (i.get() < 150) {
					i.set(i.get() + 1);
				} else {
					cancel();
				}
			}
		}.runTaskTimer(SkyPVP.getInstance(), 0, 5);
	}

	@SuppressWarnings("deprecation")
	public static void createBeacon(Location location) {
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();

		World world = location.getWorld();

		world.getBlockAt(x, y, z).setType(Material.BEACON);
		world.getBlockAt(x, y + 1, z).setType(Material.STAINED_GLASS);
		world.getBlockAt(x, y + 1, z).setData((byte) Variables.getRandom().nextInt(14));
		for (int xPoint = x - 1; xPoint <= x + 1; xPoint++) {
			for (int zPoint = z - 1; zPoint <= z + 1; zPoint++) {
				world.getBlockAt(xPoint, y - 1, zPoint).setType(Material.IRON_BLOCK);
			}
		}
	}

	public static void removeBeacon(Location location) {
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();

		World world = location.getWorld();

		world.getBlockAt(x, y, z).setType(Material.AIR);
		world.getBlockAt(x, y + 1, z).setType(Material.AIR);
		for (int xPoint = x - 1; xPoint <= x + 1; xPoint++) {
			for (int zPoint = z - 1; zPoint <= z + 1; zPoint++) {
				world.getBlockAt(xPoint, y - 1, zPoint).setType(Material.AIR);
			}
		}
	}

	public static boolean getFree(Location location) {
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();

		World world = location.getWorld();

		if (world.getBlockAt(x, y, z).getType() != Material.AIR) {
			return false;
		}
		for (int xPoint = x - 1; xPoint <= x + 1; xPoint++) {
			for (int zPoint = z - 1; zPoint <= z + 1; zPoint++) {
				if (world.getBlockAt(xPoint, y - 1, zPoint).getType() != Material.AIR) {
					return false;
				}
			}
		}
		return true;
	}

}