package me.andre111.items;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class RewardManager {
	private static HashMap<String, Integer> pointMap = new HashMap<String, Integer>();

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
		if(player.isOnline()) {
			
		}
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
