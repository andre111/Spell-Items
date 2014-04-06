package me.andre111.items.item.utils;

import me.andre111.items.SpellItems;
import me.andre111.items.item.LuaSpell;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class CreateEffect extends TwoArgFunction {
	
	@Override
	public LuaValue call(LuaValue locationN, LuaValue effectN) {
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
			Player player = Bukkit.getPlayerExact(locationN.toString());
			if(player!=null) {
				loc = player.getLocation();
			}
		}
		
		if(loc!=null && effectN.isstring()) {
			String name = effectN.toString();
			
			spell.createEffects(loc, name);
			return LuaValue.TRUE;
		}
		
		return LuaValue.FALSE;
	}

}
