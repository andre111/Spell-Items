package me.andre111.items;

import java.util.ArrayList;

import org.bukkit.Location;

import me.andre111.items.item.ItemEffect;

public class Reward {
	private int level;
	private boolean repeating;
	private boolean reset;
	
	private ArrayList<ItemEffect> effects = new ArrayList<ItemEffect>();
	
	private String item;
	
	public void createEffects(Location loc, String position) {
		//effects
		for(ItemEffect st : effects) {
			if(st!=null)
			if(st.getLocation().equals(position))
				st.play(loc);
		}
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isRepeating() {
		return repeating;
	}
	public void setRepeating(boolean repeating) {
		this.repeating = repeating;
	}
	public boolean isReset() {
		return reset;
	}
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public void addEffect(ItemEffect effect) {
		effects.add(effect);
	}
}
