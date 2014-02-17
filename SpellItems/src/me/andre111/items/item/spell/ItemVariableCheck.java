package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemVariableCheck extends ItemSpell {
	private int variable = 0;
	private SpellVariable obj;
	private int operation = 0;

	@Override
	public void setCastVar(int id, double var) {
		if(id==0) variable = (int) Math.round(var);
		else if(id==1) obj = new SpellVariable(SpellVariable.DOUBLE, (Double) var);
		else if(id==2) operation = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==1) obj = new SpellVariable(SpellVariable.STRING, var);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) variable = var.getAsInt();
		else if(id==1) obj = var;
	}
	
	//TODO - change to better support the possibilities of the new variable system
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		SpellVariable var = getVariables().get(variable);
		
		//only for integers/doubles
		if(operation>=0 && operation<=5) {
			double varD = Double.NaN;
			double objD = Double.NaN;
			
			if(var.getType().equals(SpellVariable.DOUBLE)) varD = var.getAsDouble();
			if(obj.getType().equals(SpellVariable.DOUBLE)) objD = obj.getAsDouble();
			
			if(var.getType().equals(SpellVariable.STRING)) {
				try {
					varD = Double.parseDouble(var.getAsString());
				} catch(NumberFormatException e) {
				}
			}
			if(obj.getType().equals(SpellVariable.STRING)) {
				try {
					objD = Double.parseDouble(obj.getAsString());
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
			return var.getObj().equals(obj.getObj());
		} else if(operation==7) {
			return !var.getObj().equals(obj.getObj());
		}
		
		
		return false;
	}
	
	//TODO - nicht mehr benötigt wegen lua?
}
