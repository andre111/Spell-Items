package me.andre111.items.item.spell;

import java.util.List;
import java.util.UUID;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Silverfish;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemRoar extends ItemSpell {
	//TODO - remove message and make it a separate spell
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue playerN = LUAHelper.getInternalValue(args.arg(1));
			LuaValue rangeN = args.arg(2);
			LuaValue messageN = args.arg(3);
			
			if(playerN.isuserdata(UUID.class) && rangeN.isnumber() && messageN.isstring()) {
				Entity target = EntityHandler.getEntityFromUUID((UUID) playerN.touserdata(UUID.class));
				double range = rangeN.todouble();
				String message = messageN.toString();
				
				if(target!=null) {
					castAtEntity(target, range, message);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private boolean castAtEntity(Entity ent, double range, String message) {
		boolean success = false;
		List<Entity> entities = ent.getNearbyEntities(range, range, range);
        for (Entity e : entities) {
        	if (e instanceof Silverfish) {
        		((Silverfish)e).damage((double) 0, ent);
        		success = true;
        	}
        }
		
        if(!message.equals(""))
        	//Bukkit.getServer().broadcastMessage(ConfigManager.getLanguage().getString("string_brood_roar","A Broodmother roars!"));
        	Bukkit.getServer().broadcastMessage(message);
        
        return success;
	}
}
