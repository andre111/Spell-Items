package me.andre111.items.item.spell;

import java.util.List;
import java.util.Random;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemVariableSet extends ItemSpell {
	private int variable = 0;
	private String value = "";
	
	private Random rand = new Random();
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) variable = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==1) value = var;
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) variable = var.getAsInt();
		else if(id==1) value = var.getAsString();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		//Locations
		if(value.equalsIgnoreCase("playerPos")) {
			if(player!=null)
				getVariables().put(variable, new SpellVariable(SpellVariable.LOCATION, player.getLocation()));
		} else if(value.equalsIgnoreCase("targetPos")) {
			if(target!=null)
				getVariables().put(variable, new SpellVariable(SpellVariable.LOCATION, target.getLocation()));
		} else if(value.equalsIgnoreCase("blockPos")) {
			if(block!=null)
				getVariables().put(variable, new SpellVariable(SpellVariable.LOCATION, block.getLocation()));
		} else if(value.equalsIgnoreCase("worldSpawn")) {
			if(player!=null)
				getVariables().put(variable, new SpellVariable(SpellVariable.LOCATION, player.getWorld().getSpawnLocation()));
		//Players
		} else if(value.equalsIgnoreCase("player")) {
			getVariables().put(variable, new SpellVariable(SpellVariable.STRING, player.getName())); //new SpellVariable(SpellVariable.PLAYER, player)
		} else if(value.equalsIgnoreCase("target")) {
			getVariables().put(variable, new SpellVariable(SpellVariable.STRING, target.getName())); //new SpellVariable(SpellVariable.PLAYER, target)
		} else if(value.equalsIgnoreCase("randomPlayer")) {
			if(player!=null) {
				List<Player> players = player.getWorld().getPlayers();
				int pos = rand.nextInt(players.size());
				getVariables().put(variable, new SpellVariable(SpellVariable.STRING, players.get(pos).getName())); //new SpellVariable(SpellVariable.PLAYER, players.get(pos))
			}
		//Block
		} else if(value.equalsIgnoreCase("block")) {
			getVariables().put(variable, new SpellVariable(SpellVariable.BLOCK, block));
		//Numbers
		} else if(value.equalsIgnoreCase("time")) {
			if(player!=null)
				getVariables().put(variable, new SpellVariable(SpellVariable.DOUBLE, (Double) (0.0D+player.getWorld().getTime())));
		}
		
		return true;
	}
}
