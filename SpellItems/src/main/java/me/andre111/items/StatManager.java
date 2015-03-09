package me.andre111.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.andre111.items.config.ConfigManager;
import me.andre111.items.iface.IUpCounter;
import me.andre111.items.utils.EntityHandler;
import me.andre111.items.volatileCode.SpellItemsPackets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StatManager {
	private static HashMap<UUID, IUpCounter> counters = new HashMap<UUID, IUpCounter>();
	private static HashMap<UUID, String> countervars = new HashMap<UUID, String>();
	private static HashMap<UUID, Integer> counterCurrent = new HashMap<UUID, Integer>();
	private static boolean running = false;
	
	private static HashMap<UUID, Integer> xpBarLevel = new HashMap<UUID, Integer>();
	private static HashMap<UUID, Float> xpBarXp = new HashMap<UUID, Float>();
	private static HashMap<UUID, Boolean> xpBarShown = new HashMap<UUID, Boolean>();
	
	//show the Playerstats
	public static void show(Player player) {
		//xp-bar
		xpBarShown.put(player.getUniqueId(), true);
		if(xpBarLevel.containsKey(player.getUniqueId())) {
			SpellItemsPackets.sendFakeXP(player, xpBarLevel.get(player.getUniqueId()), xpBarXp.get(player.getUniqueId()));
		}
	}
	//Hide them
	public static void hide(Player player, boolean force) {
		//don't hide when always shown s enabled
		if(!ConfigManager.getStaticConfig().getString("always_show_stats", "false").equals("true") || force) {
			//xp-bar
			xpBarShown.put(player.getUniqueId(), false);
			SpellItemsPackets.sendRealXP(player);
		}
	}
	
	//set the xp level of the player
	public static void setXPBarStat(UUID player, int level, float xp) {
		xpBarLevel.put(player, level);
		xpBarXp.put(player, xp);
		
		//check if a countup is shown
		if(counters.containsKey(player)) return;
		
		//show stats, when they should always show
		if(ConfigManager.getStaticConfig().getString("always_show_stats", "false").equals("true")) {
			Player p = EntityHandler.getPlayerFromUUID(player);
			if(p!=null) show(p);
		}
		
		if(xpBarShown.containsKey(player)) {
			if(xpBarShown.get(player)) {
				Player p = EntityHandler.getPlayerFromUUID(player);
				
				if(p!=null) {
					SpellItemsPackets.sendFakeXP(p, level, xp);
				}
			}
		}
	}
	//called, when to real xp changes(to hide the change)
	public static void updateXPBarStat(Player player) {
		//check if a countup is shown
		if(counters.containsKey(player.getUniqueId())) return;
		
		//show stats, when they should always show
		if(ConfigManager.getStaticConfig().getString("always_show_stats", "false").equals("true")) {
			show(player);
		}

		if(xpBarShown.containsKey(player.getUniqueId())) {
			if(xpBarShown.get(player.getUniqueId())) {
				if(xpBarLevel.containsKey(player.getUniqueId()) && xpBarXp.containsKey(player.getUniqueId())) {
					int level = xpBarLevel.get(player.getUniqueId());
					float xp = xpBarXp.get(player.getUniqueId());
				
					SpellItemsPackets.sendFakeXP(player, level, xp);
				}
			}
		}
	}
	
	public static void onInventoryOpen(Player player) {
		if(ConfigManager.getStaticConfig().getString("always_show_stats", "false").equals("true")) {
			SpellItemsPackets.sendRealXP(player);
		}
	}
	public static void onInventoryClose(final Player player) {
		if(ConfigManager.getStaticConfig().getString("always_show_stats", "false").equals("true")) {
			show(player);
		}
	}
	
	//reset stats for a Player
	public static void resetPlayer(UUID player) {
		xpBarXp.remove(player);
		xpBarLevel.remove(player);
		xpBarShown.remove(player);
	}
	
	//UpCounter
	public static void setCounter(UUID player, IUpCounter counter, String vars) {
		if(!running) {
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SpellItems.instance, new Runnable() {
				public void run() {
					updateCounters();
				}
			}, 20, 20);
			
			running = true;
		}
		
		if(counters.get(player)!=null)
		if(!counters.get(player).countUPOverridable()) return;
	
		interruptCounter(counters.get(player), player);
		
		counters.put(player, counter);
		countervars.put(player, vars);
		counterCurrent.put(player, 0);
	}
	public static void setCounterVars(UUID player, String vars) {
		countervars.put(player, vars);
	}
	public static void interruptMove(UUID player) {
		if(counters.containsKey(player)) {
			IUpCounter counter = counters.get(player);
			
			if(counter.countUPinterruptMove()) {
				interruptCounter(counter, player);
			}
		}
	}
	public static void interruptDamage(UUID player) {
		if(counters.containsKey(player)) {
			IUpCounter counter = counters.get(player);
			
			if(counter.countUPinterruptDamage()) {
				interruptCounter(counter, player);
			}
		}
	}
	public static void interruptItem(UUID player) {
		if(counters.containsKey(player)) {
			IUpCounter counter = counters.get(player);
			
			if(counter.countUPinterruptItemChange()) {
				interruptCounter(counter, player);
			}
		}
	}
	private static void interruptCounter(IUpCounter counter, UUID player) {
		if(counter!=null)
			counter.countUPinterrupt(countervars.get(player));
		
		counters.remove(player);
		countervars.remove(player);
		counterCurrent.remove(player);
		
		Player p = EntityHandler.getPlayerFromUUID(player);
		if(p!=null) {
			SpellItemsPackets.sendRealXP(p);
			
			//show stats, when they should always show
			if(ConfigManager.getStaticConfig().getString("always_show_stats", "false").equals("true")) {
				show(p);
			}
		}
	}
	private static void updateCounters() {
		ArrayList<UUID> remove = new ArrayList<UUID>();
		
		for(Map.Entry<UUID, IUpCounter> entry : counters.entrySet()) {
			UUID player = entry.getKey();
			IUpCounter counter = entry.getValue();
			int cu = counterCurrent.get(player);
			
			cu += counter.countUPperSecond();
			
			//remove
			if(cu>=counter.countUPgetMax()) {
				counter.countUPfinish(countervars.get(player));
				
				Player p = EntityHandler.getPlayerFromUUID(player);
				if(p!=null) {
					SpellItemsPackets.sendRealXP(p);
					
					//show stats, when they should always show
					if(ConfigManager.getStaticConfig().getString("always_show_stats", "false").equals("true")) {
						show(p);
					}
				}
				
				remove.add(player);
			}
			//send
			else {
				counterCurrent.put(player, cu);
				counter.countUPincrease(countervars.get(player));
				
				Player p = EntityHandler.getPlayerFromUUID(player);
				if(p!=null) {
					SpellItemsPackets.sendFakeXP(p, 0, ((float)cu)/counter.countUPgetMax());
				}
			}
		}
		//remove finished counters
		for(UUID player : remove) {
			counters.remove(player);
			countervars.remove(player);
			counterCurrent.remove(player);
		}
	}
}
