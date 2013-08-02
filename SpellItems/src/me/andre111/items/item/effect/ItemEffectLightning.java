package me.andre111.items.item.effect;

import me.andre111.items.item.ItemEffect;

import org.bukkit.Location;

public class ItemEffectLightning extends ItemEffect {
	
	@Override
	public void play(Location loc) {
		loc.getWorld().strikeLightningEffect(loc);
	}
}
