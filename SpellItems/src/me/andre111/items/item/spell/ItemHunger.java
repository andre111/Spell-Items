package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.ItemVariableHelper;

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
	public void setCastVar(int id, Object var) {
		if(id==0) ammount = ItemVariableHelper.getVariableAsInt(var);
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		if(target==null) {
			if(player!=null) resetCoolDown(player);
			return false;
		}
		
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
}
