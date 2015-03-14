package me.andre111.items.item.spell;

import java.util.UUID;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemHunger extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = LUAHelper.getInternalValue(args.arg(1));
			LuaValue ammountN = args.arg(2);
			
			if(playerN.isuserdata(UUID.class) && ammountN.isnumber()) {
				Entity target = EntityHandler.getEntityFromUUID((UUID) playerN.touserdata(UUID.class));
				int ammount = ammountN.toint();
				
				if(target!=null && target instanceof Player) {
					Player p = (Player) target;
					int newfood = p.getFoodLevel()-ammount;
					if(newfood<0) newfood = 0;
					
					p.setFoodLevel(newfood);
					
					//über 50 - alles entfernen
					if(ammount>50) {
						p.setFoodLevel(0);
						p.setSaturation(0);
					}
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
