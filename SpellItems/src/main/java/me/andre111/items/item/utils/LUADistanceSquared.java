package me.andre111.items.item.utils;

import me.andre111.items.utils.EntityHandler;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

public class LUADistanceSquared extends ThreeArgFunction {
	
	@Override
	public LuaValue call(LuaValue locationN, LuaValue olocationN, LuaValue ignoreYN) {
		//Get Location
		Location loc = null;
		if(locationN.isuserdata(Location.class)) {
			loc = (Location) locationN.touserdata(Location.class);
		} else if(locationN.isuserdata(Block.class)) {
			loc = ((Block) locationN.touserdata(Block.class)).getLocation();
		} else if(locationN.isstring()) {
			Entity target = EntityHandler.getEntityFromUUID(locationN.toString());
			if(target!=null) {
				loc = target.getLocation();
			}
		}
		
		//Get Location
		Location oloc = null;
		if(olocationN.isuserdata(Location.class)) {
			oloc = (Location) olocationN.touserdata(Location.class);
		} else if(olocationN.isuserdata(Block.class)) {
			oloc = ((Block) olocationN.touserdata(Block.class)).getLocation();
		} else if(olocationN.isstring()) {
			Entity target = EntityHandler.getEntityFromUUID(olocationN.toString());
			if(target!=null) {
				oloc = target.getLocation();
			}
		}
		
		boolean ignoreY = false;
		if(ignoreYN.isboolean()) {
			ignoreY = ignoreYN.toboolean();
		}
		
		if(ignoreY) {
			oloc = oloc.clone();
			oloc.setY(loc.getY());
		}
		
		if(loc!=null && oloc!=null) {
			return LuaValue.valueOf(loc.distanceSquared(oloc));
		}
		
		return LuaValue.valueOf(-1);
	}

}
