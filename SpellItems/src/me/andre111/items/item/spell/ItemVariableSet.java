package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.ItemVariableHelper;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemVariableSet extends ItemSpell {
	private int variable = 0;
	private String value = "";
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) variable = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==1) value = var;
	}
	
	@Override
	public void setCastVar(int id, Object var) {
		if(id==0) variable = ItemVariableHelper.getVariableAsInt(var);
		else if(id==1) value = ItemVariableHelper.getVariableAsString(var);
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		if(value.equalsIgnoreCase("playerPos")) {
			if(player!=null)
				getVariables().put(variable, player.getLocation());
		} else if(value.equalsIgnoreCase("targetPos")) {
			if(target!=null)
				getVariables().put(variable, target.getLocation());
		} else if(value.equalsIgnoreCase("blockPos")) {
			if(block!=null)
				getVariables().put(variable, block.getLocation());
		} else if(value.equalsIgnoreCase("player")) {
			getVariables().put(variable, player);
		} else if(value.equalsIgnoreCase("target")) {
			getVariables().put(variable, target);
		} else if(value.equalsIgnoreCase("block")) {
			getVariables().put(variable, block);
		} else if(value.equalsIgnoreCase("time")) {
			if(player!=null) {
				getVariables().put(variable, player.getWorld().getTime());
			}
		}
		
		return true;
	}
}
