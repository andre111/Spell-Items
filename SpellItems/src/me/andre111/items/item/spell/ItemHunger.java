package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemHunger extends ItemSpell {
private int ammount = 2;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) ammount = (int) Math.round(var);
	}

	@Override
	public boolean cast(Player player, Player target) {
		int newfood = target.getFoodLevel()-ammount;
		if(newfood<0) newfood = 0;
		
		target.setFoodLevel(newfood);
		
		//über 50 - alles entfernen
		if(ammount>50) {
			target.setFoodLevel(0);
			target.setSaturation(0);
		}
		
		return true;
	}
	
	@Override
	public boolean cast(Player player) {
		resetCoolDown(player);
		return false;
	}
	@Override
	public boolean cast(Player player, Block block) {
		resetCoolDown(player);
		return false;
	}
	@Override
	public boolean cast(Player player, Location loc) {
		resetCoolDown(player);
		return false;
	}
}
