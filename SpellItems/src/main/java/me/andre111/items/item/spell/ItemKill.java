package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemKill extends ItemSpell {
	/*private String playername = "";
	
	@SuppressWarnings("unused")
	private double range;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue playerN = args.arg(1);
			
			if(playerN.isstring()) {
				Entity target = EntityHandler.getEntityFromUUID(playerN.toString());
				
				if(target!=null && target instanceof LivingEntity) {
					((LivingEntity) target).setHealth(0);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}