package me.andre111.items.lua;

import java.util.UUID;

import me.andre111.items.utils.EntityHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class SendMessage extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			LuaValue message = args.arg(2);
			
			if(value.isuserdata(UUID.class) && message.isstring()) {
				Entity entity = EntityHandler.getEntityFromUUID((UUID) value.touserdata(UUID.class));
				if(entity!=null) {
					String m = ChatColor.translateAlternateColorCodes('&', message.tojstring());
					entity.sendMessage(m);
					
					return LuaValue.TRUE;
				}
			}
		}
		
		return LuaValue.FALSE;
	}
}
