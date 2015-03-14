package me.andre111.items.lua;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class GetPlayerCount extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			
			World world = null;
			if(value.isstring()) {
				world = Bukkit.getWorld(value.tojstring());
			}
			
			if(world!=null) {
				LuaValue[] returnValue = new LuaValue[2];
				returnValue[0] = LuaValue.TRUE;
				returnValue[1] = LuaValue.valueOf(world.getPlayers().size());
				
				return LuaValue.varargsOf(returnValue);
			}
		}
		
		return LuaValue.FALSE;
	}
}
