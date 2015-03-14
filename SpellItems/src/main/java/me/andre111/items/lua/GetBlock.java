package me.andre111.items.lua;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class GetBlock extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			
			Block block = null;
			if(value.isuserdata(Location.class)) {
				Location loc = (Location) value.touserdata(Location.class);
				block = loc.getBlock();
			}
			
			if(block!=null) {
				LuaValue[] returnValue = new LuaValue[2];
				returnValue[0] = LuaValue.TRUE;
				returnValue[1] = LUAHelper.createBlockObject(block);
				
				return LuaValue.varargsOf(returnValue);
			}
		}
		
		return LuaValue.FALSE;
	}
}
