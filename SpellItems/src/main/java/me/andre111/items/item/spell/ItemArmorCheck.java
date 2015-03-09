package me.andre111.items.item.spell;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemArmorCheck extends ItemSpell {
	/*private String playername = "";
	private boolean shouldHave = false;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue armorN = args.arg(2);
			
			if(playerN.isstring() && armorN.isboolean()) {
				Entity target = EntityHandler.getEntityFromUUID(playerN.toString());
				boolean shouldHave = armorN.toboolean();
				
				//TODO - X code for other entities
				if(target!=null && target instanceof Player) {
					if(ItemHandler.isArmorEmpty((Player) target)) {
						if(shouldHave) return RETURN_FALSE;
						else return RETURN_TRUE;
					} else {
						if(!shouldHave) return RETURN_FALSE;
						else return RETURN_TRUE;
					}
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
