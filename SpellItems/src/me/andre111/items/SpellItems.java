package me.andre111.items;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.andre111.items.config.ConfigManager;
import me.andre111.items.item.CustomItem;
import me.andre111.items.item.ItemManager;
import me.andre111.items.item.enchant.SpecialEnchantmentManager;
import me.andre111.items.volatileCode.DynamicClassFunctions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class SpellItems extends JavaPlugin {
	public static SpellItems instance;
	
	public static Logger logger;
	public static String prefix = "[SpellItems] ";
	
	public static ProtocolManager protocolManager;
	
	public static ItemManager itemManager;
	public static SpecialEnchantmentManager enchantManager;
	
	
	@Override
	public void onLoad() {
		logger = Logger.getLogger("Minecraft");
		
		// Dynamic package detection
		if (!DynamicClassFunctions.setPackages()) {
			logger.log(Level.WARNING, "NMS/OBC package could not be detected, using " + DynamicClassFunctions.nmsPackage + " and " + DynamicClassFunctions.obcPackage);
		}
		DynamicClassFunctions.setClasses();
		DynamicClassFunctions.setMethods();
		DynamicClassFunctions.setFields();
	}
	
	@Override
	public void onEnable() {
		if(SpellItems.instance==null)
			SpellItems.instance = this;
		
		ConfigManager.initConfig();
		
		if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib"))
		{
			Bukkit.getServer().getConsoleSender().sendMessage(prefix+" "+ChatColor.RED+"ProtocolLib could not be found, disabling...");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		SpellItems.protocolManager = ProtocolLibrary.getProtocolManager();
		
		enchantManager = new SpecialEnchantmentManager();
		enchantManager.loadEnchants(ConfigManager.getItemFile());
		itemManager = new ItemManager();
		itemManager.loadItems(ConfigManager.getItemFile());
		
		RewardManager.loadRewardPoints();
		
		new SpellItemListener(this);
		
		SpellCommandExecutor command = new SpellCommandExecutor();
		for(String st : getDescription().getCommands().keySet()) {
			getCommand(st).setExecutor(command);
		}
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				CooldownManager.tick();
				ManaManager.tick();
		    }
		}, 20, 20);
	}
	
	@Override
	public void onDisable() {
		if(SpellItems.instance!=null)
			SpellItems.instance = null;
		
		RewardManager.saveRewardPoints();
	}
	
	//used to load custom items from other plugins
	public static void loadFromConfiguration(FileConfiguration config) {
		enchantManager.addEnchants(config);
		itemManager.addItems(config);
	}
	public static void reload() {
		reloadItems();
		reloadEnchantmets();
	}
	public static void reloadItems() {
		ConfigManager.reloadConfig();
		itemManager.reload(ConfigManager.getItemFile());
	}
	public static void reloadEnchantmets() {
		ConfigManager.reloadConfig();
		enchantManager.reload(ConfigManager.getItemFile());
	}

	//#######################################
	//Spieler hat geklickt custom item
	//actions:
	//0 = leftclick
	//1 = rigthclick
	//2 = eat
	//#######################################
	public static void playerSpecialItemC(Player player, ItemStack item, int action, Block block, Player target) {
		ItemMeta im = item.getItemMeta();
		if(im!=null) {
			if(im.hasDisplayName()) {
				List<CustomItem> cil = SpellItems.itemManager.getItemByDisplayName(im.getDisplayName());
				if(cil!=null) {
					for(int i=0; i<cil.size(); i++) {
						CustomItem ci = cil.get(i);

						if(ci.isThisItem(item)) {
							if(block!=null)
								ci.cast(action, player, block);
							else if(target!=null)
								ci.cast(action, player, target);
							else
								ci.cast(action, player);
						}
					}
				}
			}
		}
	}
	
	public static void log(String s) {
		logger.info(prefix+s);
	}
	
	public static boolean isPathable(Block block) {
        return isPathable(block.getType());
	}
	public static boolean isPathable(Material material) {
		return
				material == Material.AIR ||
				material == Material.SAPLING ||
				material == Material.WATER ||
				material == Material.STATIONARY_WATER ||
				material == Material.POWERED_RAIL ||
				material == Material.DETECTOR_RAIL ||
				material == Material.LONG_GRASS ||
				material == Material.DEAD_BUSH ||
				material == Material.YELLOW_FLOWER ||
				material == Material.RED_ROSE ||
				material == Material.BROWN_MUSHROOM ||
				material == Material.RED_MUSHROOM ||
				material == Material.TORCH ||
				material == Material.FIRE ||
				material == Material.REDSTONE_WIRE ||
				material == Material.CROPS ||
				material == Material.SIGN_POST ||
				material == Material.LADDER ||
				material == Material.RAILS ||
				material == Material.WALL_SIGN ||
				material == Material.LEVER ||
				material == Material.STONE_PLATE ||
				material == Material.WOOD_PLATE ||
				material == Material.REDSTONE_TORCH_OFF ||
				material == Material.REDSTONE_TORCH_ON ||
				material == Material.STONE_BUTTON ||
				material == Material.SNOW ||
				material == Material.SUGAR_CANE_BLOCK ||
				material == Material.VINE ||
				material == Material.WATER_LILY ||
				material == Material.NETHER_STALK ||
				material == Material.TRIPWIRE_HOOK ||
				material == Material.TRIPWIRE ||
				material == Material.POTATO ||
				material == Material.CARROT ||
				material == Material.WOOD_BUTTON;
	}
	public final static HashSet<Byte> transparent = new HashSet<Byte>();
	static {
		transparent.add((byte)0);
		transparent.add((byte)6);
		transparent.add((byte)8);
		transparent.add((byte)9);
		transparent.add((byte)27);
		transparent.add((byte)28);
		transparent.add((byte)31);
		transparent.add((byte)32);
		transparent.add((byte)37);
		transparent.add((byte)38);
		transparent.add((byte)39);
		transparent.add((byte)40);
		transparent.add((byte)50);
		transparent.add((byte)51);
		transparent.add((byte)55);
		transparent.add((byte)59);
		transparent.add((byte)63);
		transparent.add((byte)65);
		transparent.add((byte)66);
		transparent.add((byte)68);
		transparent.add((byte)69);
		transparent.add((byte)70);
		transparent.add((byte)71);
		transparent.add((byte)75);
		transparent.add((byte)76);
		transparent.add((byte)77);
		transparent.add((byte)78);
		transparent.add((byte)83);
		transparent.add((byte)106);
		transparent.add((byte)111);
		transparent.add((byte)115);
		transparent.add((byte)131);
		transparent.add((byte)132);
		transparent.add((byte)141);
		transparent.add((byte)142);
		transparent.add((byte)143);
	}
	
	public final static HashSet<Player> jumpingNormal = new HashSet<Player>();
}
