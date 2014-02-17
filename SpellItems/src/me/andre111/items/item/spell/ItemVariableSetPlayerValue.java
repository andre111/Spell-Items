package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemVariableSetPlayerValue extends ItemSpell {
	/*private int variable = 0;
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
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue valueN = args.arg(2);
			
			if(playerN.isstring() && valueN.isstring()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				String value = valueN.toString();
				
				LuaValue[] returnValue = new LuaValue[2];
				returnValue[0] = LuaValue.TRUE;
				
				if(player!=null) {
					//Locations
					if(value.equalsIgnoreCase("location")) {
						returnValue[1] = LuaValue.userdataOf(player.getLocation());
					} else if(value.equalsIgnoreCase("spawn")) {
						Location sloc = player.getBedSpawnLocation();
						if(sloc==null) sloc = player.getWorld().getSpawnLocation();
						returnValue[1] = LuaValue.userdataOf(sloc);
					//Numbers
					} else if(value.equalsIgnoreCase("health")) {
						returnValue[1] = LuaValue.valueOf(player.getHealth());
					} else if(value.equalsIgnoreCase("food")) {
						returnValue[1] = LuaValue.valueOf(0.0D+player.getFoodLevel());
					} else if(value.equalsIgnoreCase("saturation")) {
						returnValue[1] = LuaValue.valueOf(0.0D+player.getSaturation());
					} else if(value.equalsIgnoreCase("gamemode")) {
						returnValue[1] = LuaValue.valueOf(0.0D+player.getGameMode().getValue());
					}
					return LuaValue.varargsOf(returnValue);
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
