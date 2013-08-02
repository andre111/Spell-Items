package me.andre111.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CooldownManager {
	private static HashMap<String, Integer> customCooldown = new HashMap<String, Integer>();
	
	public static void tick() {
		//save for cuncurrentmodification
		ArrayList<String> remove = new ArrayList<String>();
		for(Map.Entry<String, Integer> e : customCooldown.entrySet()){
			String key = e.getKey();
			int time = e.getValue();

			time--; 
			customCooldown.put(key, time);
			if(time<=0) remove.add(key);
		}
		for(String st : remove) {
			customCooldown.remove(st);
		}
	}
	
	
	public static void setCustomCooldown(String player, String name, int time) {
		customCooldown.put(player+":"+name, time);
	}
	public static int getCustomCooldown(String player, String name) {
		if(customCooldown.containsKey(player+":"+name)) {
			return customCooldown.get(player+":"+name);
		}
		
		return -1;
	}
	public static void resetCustomCooldown(String player, String name) {
		customCooldown.remove(player+":"+name);
	}
}
