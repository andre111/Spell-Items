package me.andre111.items.item.spell;

import java.util.Random;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.ItemVariableHelper;

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
	public void setCastVar(int id, Object var) {
		if(id==0) variable = ItemVariableHelper.getVariableAsInt(var);
		else if(id==1) minValue = ItemVariableHelper.getVariableAsDouble(var);
		else if(id==2) maxValue = ItemVariableHelper.getVariableAsDouble(var);
		else if(id==3) decimals = ItemVariableHelper.getVariableAndIntegerBoolean(var);
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		if(decimals) {
			getVariables().put(variable, minValue+rand.nextDouble()*(maxValue-minValue));
		} else {
			int random = (int) (minValue) + rand.nextInt((int)maxValue - (int)minValue);
			getVariables().put(variable, random);
		}
		
		return true;
	}
}
