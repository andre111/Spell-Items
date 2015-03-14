package me.andre111.items.lua;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class SetTime extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			LuaValue time = args.arg(2);
			
			if(value.isstring() && time.isnumber()) {
				World world = Bukkit.getWorld(value.tojstring());
				if(world!=null) {
					world.setTime(time.tolong());
					return LuaValue.TRUE;
				}
			}
		}
		
		return LuaValue.FALSE;
	}
}
