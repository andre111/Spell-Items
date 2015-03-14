package me.andre111.items.lua;

import me.andre111.items.SpellItems;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class LUAHelper {
	public static LuaValue getInternalValue(LuaValue value) {
		if(value.istable()) {
			LuaTable table = (LuaTable) value;
			
			return table.get("internal");
		}
		
		return LuaValue.NIL;
	}
	
	
	
	public static LuaValue createBlockObject(Block block) {
		Globals globals = SpellItems.luacontroller.globals;
		
		LuaValue locValue = globals.get("Block");
		if(block!=null) {
			return locValue.get("new").invoke(locValue, LuaValue.userdataOf(block)).arg(1);
		} else {
			return locValue.get("new").invoke(locValue, LuaValue.NIL).arg(1);
		}
	}
	
	public static LuaValue createEntityObject(Entity ent) {
		Globals globals = SpellItems.luacontroller.globals;
		
		LuaValue locValue = globals.get("Entity");
		if(ent!=null) {
			return locValue.get("new").invoke(locValue, LuaValue.userdataOf(ent.getUniqueId())).arg(1);
		} else {
			return locValue.get("new").invoke(locValue, LuaValue.NIL).arg(1);
		}
	}
	
	public static LuaValue createLocationObject(Location loc) {
		Globals globals = SpellItems.luacontroller.globals;
		
		LuaValue locValue = globals.get("Location");
		if(loc!=null) {
			return locValue.get("new").invoke(locValue, LuaValue.userdataOf(loc)).arg(1);
		} else {
			return locValue.get("new").invoke(locValue, LuaValue.NIL).arg(1);
		}
	}
	
	public static LuaValue createWorldObject(World world) {
		Globals globals = SpellItems.luacontroller.globals;
		
		LuaValue locValue = globals.get("World");
		if(world!=null) {
			return locValue.get("new").invoke(locValue, LuaValue.valueOf(world.getName())).arg(1);
		} else {
			return locValue.get("new").invoke(locValue, LuaValue.NIL).arg(1);
		}
	}
}
