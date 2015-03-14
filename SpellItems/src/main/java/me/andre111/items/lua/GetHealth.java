package me.andre111.items.lua;

import java.util.UUID;

import me.andre111.items.utils.EntityHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class GetHealth extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			
			if(value.isuserdata(UUID.class)) {
				Entity entity = EntityHandler.getEntityFromUUID((UUID) value.touserdata(UUID.class));
				if(entity!=null && entity instanceof LivingEntity) {
					LuaValue[] returnValue = new LuaValue[2];
					returnValue[0] = LuaValue.TRUE;
					returnValue[1] = LuaValue.valueOf(((LivingEntity) entity).getHealth());
					
					return LuaValue.varargsOf(returnValue);
				}
			}
		}
		
		return LuaValue.FALSE;
	}
}
