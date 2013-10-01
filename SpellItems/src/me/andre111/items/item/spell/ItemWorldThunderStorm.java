package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.world.WorldThunderStorm;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemWorldThunderStorm extends ItemSpell {
	private int time = 10*20;
	private int chance = 8;
	private int distance = 10;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) time = (int) Math.round(var);
		if(id==1) chance = (int) Math.round(var);
		if(id==2) distance = (int) Math.round(var);
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		return castIntern(loc);
	}
	
	private boolean castIntern(Location loc) {
		WorldThunderStorm effect = new WorldThunderStorm(chance, distance);
		effect.start(loc.getWorld(), loc, time);
		
		return true;
	}
}
