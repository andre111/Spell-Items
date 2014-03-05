package me.andre111.items;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManaManager {
	private static HashMap<UUID, Integer> mana = new HashMap<UUID, Integer>();
	private static HashMap<UUID, Integer> manaRegen = new HashMap<UUID, Integer>();
	private static HashMap<UUID, Integer> maxMana = new HashMap<UUID, Integer>();
	
	public static void tick() {
		//regenerate mana
		for(Map.Entry<UUID, Integer> e : mana.entrySet()){
			UUID player = e.getKey();
			int m = e.getValue();
			
			if(maxMana==null || player==null || !maxMana.containsKey(player)) continue;
	
			int maxm = maxMana.get(player);
			
			if(m<maxm) {
				m += manaRegen.get(player);
				if(m>maxm) m = maxm;
				
				changedMana(player, m, maxm);
				
				mana.put(player, m);
			}
		}
	}
	
	public static void reset() {
		mana.clear();
		manaRegen.clear();
		maxMana.clear();
	}
	
	public static void setMaxMana(UUID player, int maxM, boolean refill) {
		maxMana.put(player, maxM);
		if(refill) {
			mana.put(player, maxM);
			changedMana(player, maxM, maxM);
		}
	}
	
	public static void setManaRegen(UUID player, int regen) {
		manaRegen.put(player, regen);
	}
	
	public static int getMana(UUID player) {
		if(!mana.containsKey(player)) return 0;
		
		return mana.get(player);
	}
	
	public static void substractMana(UUID player, int ammount) {
		int value = 0;
		if(mana.containsKey(player)) value = mana.get(player);
		
		value -= ammount;
		if(value<0) value = 0;
		
		mana.put(player, value);
		
		if(maxMana!=null && player!=null && maxMana.containsKey(player) && maxMana.get(player)!=null);
			changedMana(player, value, maxMana.get(player));
	}
	
	//update mana stat
	private static void changedMana(UUID player, int ammount, int max) {
		//StatManager.setStat(player, DvZ.getLanguage().getString("scoreboard_mana", "§5Mana"), ammount);
		//new XP-Bar system
		StatManager.setXPBarStat(player, ammount, (float)ammount/(float)max);
	}
}
