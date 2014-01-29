package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemVariableSetPlayerValue extends ItemSpell {
	private int variable = 0;
	private String playername = "";
	private String value = "";
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) variable = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==1) playername = var;
		else if(id==2) value = var;
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) variable = var.getAsInt();
		else if(id==1) playername = var.getAsString();
		else if(id==2) value = var.getAsString();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Player tplayer = Bukkit.getPlayerExact(playername);
		if(tplayer!=null) {
			//Locations
			if(value.equalsIgnoreCase("location")) {
				getVariables().put(variable, new SpellVariable(SpellVariable.LOCATION, tplayer.getLocation()));
			} else if(value.equalsIgnoreCase("spawn")) {
				Location sloc = tplayer.getBedSpawnLocation();
				if(sloc==null) sloc = tplayer.getWorld().getSpawnLocation();
				getVariables().put(variable, new SpellVariable(SpellVariable.LOCATION, sloc));
			//Numbers
			} else if(value.equalsIgnoreCase("health")) {
				getVariables().put(variable, new SpellVariable(SpellVariable.DOUBLE, (Double) tplayer.getHealth()));
			} else if(value.equalsIgnoreCase("food")) {
				getVariables().put(variable, new SpellVariable(SpellVariable.DOUBLE, (Double) (0.0D+tplayer.getFoodLevel())));
			} else if(value.equalsIgnoreCase("saturation")) {
				getVariables().put(variable, new SpellVariable(SpellVariable.DOUBLE, (Double) (0.0D+tplayer.getSaturation())));
			} else if(value.equalsIgnoreCase("gamemode")) {
				getVariables().put(variable, new SpellVariable(SpellVariable.DOUBLE, (Double) (0.0D+tplayer.getGameMode().getValue())));
			}
			return true;
		}
		
		return false;
	}
}
