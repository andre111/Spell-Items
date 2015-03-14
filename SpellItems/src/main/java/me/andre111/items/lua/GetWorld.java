package me.andre111.items.lua;

import org.bukkit.Location;
import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class GetWorld extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			
			World world = null;
			if(value.isuserdata(Location.class)) {
				Location loc = (Location) value.touserdata(Location.class);
				world = loc.getWorld();
			}
			
			if(world!=null) {
				LuaValue[] returnValue = new LuaValue[2];
				returnValue[0] = LuaValue.TRUE;
				returnValue[1] = LUAHelper.createWorldObject(world);
				
				return LuaValue.varargsOf(returnValue);
			}
		}
		
		return LuaValue.FALSE;
	}
}
