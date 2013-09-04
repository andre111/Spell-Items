package me.andre111.items;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class RewardManager {
	private static HashMap<String, Integer> pointMap = new HashMap<String, Integer>();
	private static ArrayList<Reward> rewards = new ArrayList<Reward>();

	//give a player reward points
	public static void addRewardPoints(Player player, int ammount) {
		for(int i=0; i<ammount; i++) {
			addRewardPoint(player);
		}
	}
	private static void addRewardPoint(Player player) {
		if(pointMap.containsKey(player.getName())) {
			pointMap.put(player.getName(), pointMap.get(player.getName())+1);
		} else {
			pointMap.put(player.getName(), 1);
		}
		
		giveOutReward(player);
	}
	
	//gives out rewards when the required ammount is reached
	private static void giveOutReward(Player player) {
		int pPoints = pointMap.get(player.getName());
		boolean reset = false;
		
		if(player.isOnline()) {
			for(Reward r : rewards) {
				int level = r.getLevel();
				boolean repeating = r.isRepeating();
				
				if(level==pPoints || (repeating && pPoints%level==0)) {
					player.getInventory().addItem(ItemHandler.decodeItem(r.getItem()));
					
					if(r.isReset()) reset = true;
				}
			}
		}
		
		if (reset) {
			pointMap.put(player.getName(), 0);
		}
	}
	
	public static void addRewards(FileConfiguration df) {
		//rewards
		ConfigurationSection as = df.getConfigurationSection("rewards");
		if(as==null) return;
		Set<String> strings2 = as.getKeys(false);
		String[] stK2 = strings2.toArray(new String[strings2.size()]);
		//load rewards
		for(int i=0; i<stK2.length; i++) {
			loadReward(df, stK2[i]);
		}
	}	
	private static void loadReward(FileConfiguration df, String rd) {
		Reward rwTemp = new Reward();
		rwTemp.setLevel(df.getInt("rewards."+rd+".points", 1));
		rwTemp.setRepeating(df.getBoolean("rewards."+rd+".repeating", false));
		rwTemp.setReset(df.getBoolean("rewards."+rd+".reset", false));
		rwTemp.setItem(df.getString("rewards."+rd+".item", ""));
		
		rewards.add(rwTemp);
	}
	public static void clearRewards() {
		rewards.clear();
	}
	
	//save and load reward data
	public static void saveRewardPoints() {
		File file = new File(SpellItems.instance.getDataFolder(), "rewardData.yml");
		if (!file.exists()) file.mkdirs();
		YamlConfiguration rewardFile = YamlConfiguration.loadConfiguration(file);
		
		for(String player : pointMap.keySet()) {
			rewardFile.set(player, pointMap.get(player));
		}
	}
	public static void loadRewardPoints() {
		File file = new File(SpellItems.instance.getDataFolder(), "rewardData.yml");
		if (!file.exists()) file.mkdirs();
		YamlConfiguration rewardFile = YamlConfiguration.loadConfiguration(file);
		
		for (Entry<String, Object> m : rewardFile.getValues(false).entrySet()) {
			pointMap.put(m.getKey(), Integer.parseInt((String)m.getValue()));
		}
	}
}
