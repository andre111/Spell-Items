package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemVariableCheckNull extends ItemSpell {
	private int variable = 0;
	private boolean isNull = false;

	@Override
	public void setCastVar(int id, double var) {
		if(id==0) variable = (int) Math.round(var);
		else if(id==1) isNull = (var==1);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) variable = var.getAsInt();
		else if(id==1) isNull = var.getAsIntBoolean();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		if(getVariables().containsKey(variable)) {
			if(getVariables().get(variable).getObj()==null)
				return isNull;
			else if(getVariables().get(variable).getObj()!=null) 
				return !isNull;
		}
		
		if(isNull)
			return true;
		else
			return false;
	}
}
