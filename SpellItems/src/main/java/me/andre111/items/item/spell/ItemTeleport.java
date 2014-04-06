package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemTeleport extends ItemSpell {
	/*private String playername = "";
	private Location targetLoc = null;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue locN = args.arg(2);
			
			if(playerN.isstring() && locN.isuserdata(Location.class)) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				Location targetLoc = (Location) locN.touserdata(Location.class);
				
				if(player!=null && targetLoc!=null) {
					player.teleport(targetLoc);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
