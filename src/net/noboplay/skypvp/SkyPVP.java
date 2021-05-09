package net.noboplay.skypvp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import net.noboplay.skypvp.commands.BuildCommand;
import net.noboplay.skypvp.commands.CreateCommand;
import net.noboplay.skypvp.commands.EggCommand;
import net.noboplay.skypvp.commands.EnderchestCommand;
import net.noboplay.skypvp.commands.EventCommand;
import net.noboplay.skypvp.commands.FixCommand;
import net.noboplay.skypvp.commands.FriedenCommand;
import net.noboplay.skypvp.commands.InvseeCommand;
import net.noboplay.skypvp.commands.MoneyCommand;
import net.noboplay.skypvp.commands.PosCommand;
import net.noboplay.skypvp.commands.SetspawnCommand;
import net.noboplay.skypvp.commands.SpawnCommand;
import net.noboplay.skypvp.commands.SpectateCommand;
import net.noboplay.skypvp.commands.StatsCommand;
import net.noboplay.skypvp.commands.TopCommand;
import net.noboplay.skypvp.commands.TpCommand;
import net.noboplay.skypvp.listener.BlockBreakListener;
import net.noboplay.skypvp.listener.BlockBurnListener;
import net.noboplay.skypvp.listener.BlockPlaceListener;
import net.noboplay.skypvp.listener.ChatListener;
import net.noboplay.skypvp.listener.EntityDamageByEntityListener;
import net.noboplay.skypvp.listener.EntityDamageListener;
import net.noboplay.skypvp.listener.InventoryClickListener;
import net.noboplay.skypvp.listener.InventoryCloseListener;
import net.noboplay.skypvp.listener.InventoryOpenListener;
import net.noboplay.skypvp.listener.PlayerCommandPreprocessListener;
import net.noboplay.skypvp.listener.PlayerDeathListener;
import net.noboplay.skypvp.listener.PlayerInteractEntityListener;
import net.noboplay.skypvp.listener.PlayerInteractListener;
import net.noboplay.skypvp.listener.PlayerJoinListener;
import net.noboplay.skypvp.listener.PlayerLoginListener;
import net.noboplay.skypvp.listener.PlayerQuitListener;
import net.noboplay.skypvp.listener.PlayerRespawnListener;
import net.noboplay.skypvp.listener.SignChangeListener;
import net.noboplay.skypvp.listener.WeatherChangeListener;
import net.noboplay.skypvp.util.ClearLagUtil;
import net.noboplay.skypvp.util.Cuboid;
import net.noboplay.skypvp.util.EventUtil;
import net.noboplay.skypvp.util.MySQL;
import net.noboplay.skypvp.util.RegionFileManager;
import net.noboplay.skypvp.util.SpawnUtil;
import net.noboplay.skypvp.util.TreasureUtil;

public class SkyPVP extends JavaPlugin {

	/* String */
	public final String PREFIX = "§3SkyPVP §8» §7";
	public final String NOPERM = "§cKeine Rechte.";

	/* ArrayList */
	public List<Cuboid> region = new ArrayList<Cuboid>();
	public List<String> build = new ArrayList<String>();
	public List<String> spectate = new ArrayList<String>();
	public List<String> log = new ArrayList<String>();

	/* HashMap */
	public Map<Player, Location> pos1 = new HashMap<Player, Location>();
	public Map<Player, Location> pos2 = new HashMap<Player, Location>();
	public Map<String, Double> killstreak = new HashMap<String, Double>();
	public Map<String, Long> kit = new HashMap<String, Long>();
	public Map<String, Vector> spawn = new HashMap<String, Vector>();
	public Map<String, BukkitRunnable> spawntask = new HashMap<String, BukkitRunnable>();
	public Map<String, BukkitRunnable> logtask = new HashMap<String, BukkitRunnable>();

	/* SkyPVP */
	private static SkyPVP instance;

	/* Cache */
	public Cache<String, String> exp = CacheBuilder.newBuilder().concurrencyLevel(4).weakKeys()
			.expireAfterWrite(5, TimeUnit.SECONDS).build();

	@Override
	public void onEnable() {
		instance = this;
		initConfig();
		initMySQL();
		registerCommands();
		registerListener(getServer().getPluginManager());
		RegionFileManager.registerRegion();
		ClearLagUtil.startCountdown();
		TreasureUtil.fillItemList();
		EventUtil.fillItemList();
		TreasureUtil.startThread();
		SpawnUtil.startAskingScheduler();
		System.out.println("SkyPVP » Plugin geladen!");
	}

	@Override
	public void onDisable() {
		MySQL.close();
		System.out.println("SkyPVP » Plugin deaktiviert!");
	}

	private void registerCommands() {
		getCommand("build").setExecutor(new BuildCommand());
		getCommand("create").setExecutor(new CreateCommand());
		getCommand("egg").setExecutor(new EggCommand());
		getCommand("enderchest").setExecutor(new EnderchestCommand());
		getCommand("fix").setExecutor(new FixCommand());
		getCommand("invsee").setExecutor(new InvseeCommand());
		getCommand("money").setExecutor(new MoneyCommand());
		getCommand("setspawn").setExecutor(new SetspawnCommand());
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("stats").setExecutor(new StatsCommand());
		getCommand("top").setExecutor(new TopCommand());
		getCommand("pos").setExecutor(new PosCommand());
		getCommand("event").setExecutor(new EventCommand());
		getCommand("spectate").setExecutor(new SpectateCommand());
		getCommand("frieden").setExecutor(new FriedenCommand());
		getCommand("frieden").setAliases(Arrays.asList("peace"));
		getCommand("tp").setExecutor(new TpCommand());
	}

	private void registerListener(PluginManager pm) {
		pm.registerEvents(new ChatListener(), this);
		pm.registerEvents(new BlockBreakListener(), this);
		pm.registerEvents(new BlockPlaceListener(), this);
		pm.registerEvents(new EntityDamageListener(), this);
		pm.registerEvents(new EntityDamageByEntityListener(), this);
		pm.registerEvents(new InventoryClickListener(), this);
		pm.registerEvents(new InventoryCloseListener(), this);
		pm.registerEvents(new InventoryOpenListener(), this);
		pm.registerEvents(new PlayerCommandPreprocessListener(), this);
		pm.registerEvents(new PlayerDeathListener(), this);
		pm.registerEvents(new PlayerInteractListener(), this);
		pm.registerEvents(new PlayerInteractEntityListener(), this);
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		pm.registerEvents(new PlayerRespawnListener(), this);
		pm.registerEvents(new WeatherChangeListener(), this);
		pm.registerEvents(new SignChangeListener(), this);
		pm.registerEvents(new BlockBurnListener(), this);
		pm.registerEvents(new PlayerLoginListener(), this);
	}

	private void initConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	private void initMySQL() {
		MySQL.connect();
		try {
			MySQL.getConnection()
					.prepareStatement(
							"CREATE TABLE IF NOT EXISTS Stats(UUID varchar(64), KILLS int, DEATHS int, UNIQUE (UUID));")
					.executeUpdate();
			MySQL.getConnection()
					.prepareStatement("CREATE TABLE IF NOT EXISTS Money(UUID varchar(64), MONEY int, UNIQUE (UUID));")
					.executeUpdate();
			MySQL.getConnection()
					.prepareStatement(
							"CREATE TABLE IF NOT EXISTS Kits(UUID varchar(64), kit4 BOOL, kit5 BOOL, kit6 BOOL, kit7 BOOL, kit8 BOOL, kit9 BOOL, kit10 BOOL, kit11 BOOL, kit12 BOOL, UNIQUE (UUID));")
					.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static SkyPVP getInstance() {
		return instance;
	}

}
