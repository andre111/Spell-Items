package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemExperience extends ItemSpell {
	private boolean self = true;
	private int amount = 3;

	@Override
	public void setCastVar(int id, double var) {
		if(id==0) self = var==1;
		if(id==1) amount = (int) Math.round(var);
	}
	
	
	@Override
	public boolean cast(Player player) {
		if(self) {
			player.giveExp(amount);
			return true;
		}
		return false;
	}
	@Override
	public boolean cast(Player player, Block block) {
		return cast(player);
	}
	@Override
	public boolean cast(Player player, Player target) {
		if(!self) {
			target.giveExp(amount);
			return true;
		}
		
		return cast(player);
	}
	@Override
	public boolean cast(Player player, Location loc) {
		return cast(player);
	}
}
