package me.andre111.items.item.utils;

import me.andre111.items.utils.EntityHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class LUASendMessage extends TwoArgFunction {
	
	@Override
	public LuaValue call(LuaValue playerN, LuaValue messageN) {
		//Get Location
		if(playerN.isstring() && messageN.isstring()) {
			Entity target = EntityHandler.getEntityFromUUID(playerN.toString());
			if(target!=null) {
				target.sendMessage(ChatColor.translateAlternateColorCodes('&', messageN.toString()));
				
				return LuaValue.TRUE;
			}
		}
		
		return LuaValue.FALSE;
	}

}
