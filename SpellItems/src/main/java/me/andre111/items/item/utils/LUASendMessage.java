package me.andre111.items.item.utils;

import me.andre111.items.utils.PlayerHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class LUASendMessage extends TwoArgFunction {
	
	@Override
	public LuaValue call(LuaValue playerN, LuaValue messageN) {
		//Get Location
		if(playerN.isstring() && messageN.isstring()) {
			Player player = PlayerHandler.getPlayerFromUUID(playerN.toString());
			if(player!=null) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageN.toString()));
				
				return LuaValue.TRUE;
			}
		}
		
		return LuaValue.FALSE;
	}

}
