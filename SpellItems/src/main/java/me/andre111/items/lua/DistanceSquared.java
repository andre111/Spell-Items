package me.andre111.items.lua;

import org.bukkit.Location;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class DistanceSquared extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			LuaValue value2 = LUAHelper.getInternalValue(args.arg(2));
			LuaValue bool = args.arg(3);
			
			double distance = -1;
			if(value.isuserdata(Location.class) && value2.isuserdata(Location.class) && bool.isboolean()) {
				Location loc = (Location) value.touserdata(Location.class);
				Location loc2 = (Location) value2.touserdata(Location.class);
				boolean ignoreY = bool.toboolean();
				
				if(ignoreY) {
					loc2 = loc2.clone();
					loc2.setY(loc.getY());
				}
				
				distance = loc.distanceSquared(loc2);
			}
			
			if(distance!=-1) {
				LuaValue[] returnValue = new LuaValue[2];
				returnValue[0] = LuaValue.TRUE;
				returnValue[1] = LuaValue.valueOf(distance);
				
				return LuaValue.varargsOf(returnValue);
			}
		}
		
		return LuaValue.FALSE;
	}
}
