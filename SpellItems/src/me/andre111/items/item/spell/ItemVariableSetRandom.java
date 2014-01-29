package me.andre111.items.item.spell;

import java.util.Random;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemVariableSetRandom extends ItemSpell {
	private int variable = 0;
	private double minValue = 0;
	private double maxValue = 0;
	private boolean decimals = false;
	
	private Random rand = new Random();

	@Override
	public void setCastVar(int id, double var) {
		if(id==0) variable = (int) Math.round(var);
		else if(id==1) minValue = var;
		else if(id==2) maxValue = var;
		else if(id==3) decimals = (var==1);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) variable = var.getAsInt();
		else if(id==1) minValue = var.getAsDouble();
		else if(id==2) maxValue = var.getAsDouble();
		else if(id==3) decimals = var.getAsIntBoolean();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		if(decimals) {
			double random = minValue+rand.nextDouble()*(maxValue-minValue);
			getVariables().put(variable, new SpellVariable(SpellVariable.DOUBLE, (Double) random));
		} else {
			double random = (int) (minValue) + rand.nextInt((int)maxValue - (int)minValue);
			getVariables().put(variable, new SpellVariable(SpellVariable.DOUBLE, (Double) random));
		}
		
		return true;
	}
}
