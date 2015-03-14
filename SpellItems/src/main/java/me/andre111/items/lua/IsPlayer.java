package me.andre111.items.lua;

import java.util.UUID;

import me.andre111.items.utils.EntityHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class IsPlayer extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			
			if(value.isuserdata(UUID.class)) {
				Entity entity = EntityHandler.getEntityFromUUID((UUID) value.touserdata(UUID.class));
				if(entity!=null && entity instanceof Player) {
					return LuaValue.TRUE;
				}
			}
		}
		
		return LuaValue.FALSE;
	}
}
