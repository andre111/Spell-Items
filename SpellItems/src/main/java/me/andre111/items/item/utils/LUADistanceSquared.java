package me.andre111.items.item.utils;

import me.andre111.items.SpellItems;
import me.andre111.items.item.LuaSpell;
import me.andre111.items.utils.PlayerHandler;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class LUADistanceSquared extends TwoArgFunction {
	
	@Override
	public LuaValue call(LuaValue locationN, LuaValue olocationN) {
		if(!SpellItems.luacontroller.globals.get("utils").get("currentSpell").isuserdata(LuaSpell.class)) {
			return LuaValue.FALSE;
		}
		LuaSpell spell = (LuaSpell) SpellItems.luacontroller.globals.get("utils").get("currentSpell").touserdata(LuaSpell.class);
		
		//Get Location
		Location loc = null;
		if(locationN.isuserdata(Location.class)) {
			loc = (Location) locationN.touserdata(Location.class);
		} else if(locationN.isuserdata(Block.class)) {
			loc = ((Block) locationN.touserdata(Block.class)).getLocation();
		} else if(locationN.isstring()) {
			Player player = PlayerHandler.getPlayerFromUUID(locationN.toString());
			if(player!=null) {
				loc = player.getLocation();
			}
		}
		
		//Get Location
		Location oloc = null;
		if(olocationN.isuserdata(Location.class)) {
			oloc = (Location) olocationN.touserdata(Location.class);
		} else if(olocationN.isuserdata(Block.class)) {
			oloc = ((Block) olocationN.touserdata(Block.class)).getLocation();
		} else if(olocationN.isstring()) {
			Player player = PlayerHandler.getPlayerFromUUID(olocationN.toString());
			if(player!=null) {
				oloc = player.getLocation();
			}
		}
		
		if(loc!=null && oloc!=null) {
			return LuaValue.valueOf(loc.distanceSquared(oloc));
		}
		
		return LuaValue.valueOf(-1);
	}

}
