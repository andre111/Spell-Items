package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.ItemVariableHelper;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemVariableCheck extends ItemSpell {
	private int variable = 0;
	private Object obj;
	private int operation = 0;

	@Override
	public void setCastVar(int id, double var) {
		if(id==0) variable = (int) Math.round(var);
		else if(id==1) obj = var;
		else if(id==2) operation = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==1) obj = var;
	}
	
	@Override
	public void setCastVar(int id, Object var) {
		if(id==0) variable = ItemVariableHelper.getVariableAsInt(var);
		else if(id==1) obj = var;
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Object var = getVariables().get(variable);
		
		//only for integers/doubles
		if(operation>=0 && operation<=5) {
			double varD = Double.NaN;
			double objD = Double.NaN;
			
			if(var instanceof Double) varD = ((Double) var).doubleValue();
			if(obj instanceof Double) objD = ((Double) obj).doubleValue();
			
			if(var instanceof String) {
				try {
					varD = Double.parseDouble((String) var);
				} catch(NumberFormatException e) {
				}
			}
			if(obj instanceof String) {
				try {
					objD = Double.parseDouble((String) obj);
				} catch(NumberFormatException e) {
				}
			}
			
			if(operation==0) return varD==objD;
			else if(operation==1) return varD>objD;
			else if(operation==2) return varD<objD;
			else if(operation==3) return varD>=objD;
			else if(operation==4) return varD<=objD;
			else if(operation==5) return varD!=objD;
		}
		else if(operation==6) {
			return var.equals(obj);
		} else if(operation==7) {
			return !var.equals(obj);
		}
		
		
		return false;
	}
}
