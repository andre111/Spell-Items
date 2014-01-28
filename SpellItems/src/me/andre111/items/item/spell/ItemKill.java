package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.ItemVariableHelper;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemKill extends ItemSpell {
	private boolean self = true;
	
	@SuppressWarnings("unused")
	private double range;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) self = var==1;
		else if(id==1) range = var;
	}
	
	@Override
	public void setCastVar(int id, Object var) {
		if(id==0) self = ItemVariableHelper.getVariableAndIntegerBoolean(var);
		else if(id==1) range = ItemVariableHelper.getVariableAsDouble(var);
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Player pTarget = null;
		if(self) {
			pTarget = player;
		} else if(target!=null) {
			pTarget = target;
		}
		
		if(pTarget!=null) {
			castIntern(pTarget, player);
		}
		
		return false;
	}
	
	
	private boolean castIntern(Player player, Player source) {
		player.setHealth(0);
		
		return true;
	}
}