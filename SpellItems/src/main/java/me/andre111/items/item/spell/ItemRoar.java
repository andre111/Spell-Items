package me.andre111.items.item.spell;

import java.util.List;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.PlayerHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemRoar extends ItemSpell {
	/*private double range;
	private String message = "";*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue playerN = args.arg(1);
			LuaValue rangeN = args.arg(2);
			LuaValue messageN = args.arg(3);
			
			if(playerN.isstring() && rangeN.isnumber() && messageN.isstring()) {
				Player player = PlayerHandler.getPlayerFromUUID(playerN.toString());
				double range = rangeN.todouble();
				String message = messageN.toString();
				
				if(player!=null) {
					castAtEntity(player, player, range, message);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private boolean castAtEntity(Entity ent, Player damage, double range, String message) {
		boolean success = false;
		List<Entity> entities = ent.getNearbyEntities(range, range, range);
        for (Entity e : entities) {
        	if (e instanceof Silverfish) {
        		((Silverfish)e).damage((double) 0, damage);
        		success = true;
        	}
        }
		
        if(!message.equals(""))
        	//Bukkit.getServer().broadcastMessage(ConfigManager.getLanguage().getString("string_brood_roar","A Broodmother roars!"));
        	Bukkit.getServer().broadcastMessage(message);
        
        return success;
	}
}
