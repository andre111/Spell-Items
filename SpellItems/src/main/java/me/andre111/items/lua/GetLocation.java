package me.andre111.items.lua;

import java.util.UUID;

import me.andre111.items.utils.EntityHandler;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class GetLocation extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			
			Location loc = null;
			if(value.isuserdata(UUID.class)) {
				Entity entity = EntityHandler.getEntityFromUUID((UUID) value.touserdata(UUID.class));
				if(entity!=null) {
					loc = entity.getLocation();
				}
			} else if(value.isuserdata(Block.class)) {
				Block block = (Block) value.touserdata(Block.class);
				loc = block.getLocation();
			}
			
			if(loc!=null) {
				LuaValue[] returnValue = new LuaValue[2];
				returnValue[0] = LuaValue.TRUE;
				returnValue[1] = LUAHelper.createLocationObject(loc);
				
				return LuaValue.varargsOf(returnValue);
			}
		}
		
		return LuaValue.FALSE;
	}
}
