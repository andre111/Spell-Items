package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemSetDamage extends ItemSpell {
	/*private int damage = 0;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue damageN = args.arg(1);
			
			if(playerN.isstring() && damageN.isnumber()) {
				Entity target = EntityHandler.getEntityFromUUID(playerN.toString());
				int damage = damageN.toint();
				
				//TODO - X code for other entities
				if(target!=null && target instanceof Player) {
					ItemStack it = ((Player) target).getItemInHand();
					it.setDurability((short) damage);
					((Player) target).setItemInHand(it);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
