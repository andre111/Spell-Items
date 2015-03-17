package me.andre111.items.lua;

import org.bukkit.Location;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class GetCoordinates extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			
			if(value.isuserdata(Location.class)) {
				Location loc = (Location) value.touserdata(Location.class);
				
				LuaValue[] returnValue = new LuaValue[6];
				returnValue[0] = LuaValue.TRUE;
				returnValue[1] = LuaValue.valueOf(loc.getX());
				returnValue[2] = LuaValue.valueOf(loc.getY());
				returnValue[3] = LuaValue.valueOf(loc.getZ());
				returnValue[4] = LuaValue.valueOf(loc.getYaw());
				returnValue[5] = LuaValue.valueOf(loc.getPitch());
				
				return LuaValue.varargsOf(returnValue);
			}
		}
		
		return LuaValue.FALSE;
	}
}
