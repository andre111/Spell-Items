package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.PlayerHandler;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemVariableSetPlayerValue extends ItemSpell {
	/*private int variable = 0;
	private String playername = "";
	private String value = "";*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue valueN = args.arg(2);
			
			if(playerN.isstring() && valueN.isstring()) {
				Player player = PlayerHandler.getPlayerFromUUID(playerN.toString());
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
