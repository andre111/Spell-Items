package me.andre111.items.item.spell;

import java.util.UUID;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemDrop extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue playerN = LUAHelper.getInternalValue(args.arg(1));
			
			if(playerN.isuserdata(UUID.class)) {
				Entity target = EntityHandler.getEntityFromUUID((UUID) playerN.touserdata(UUID.class));
				
				//TODO - X code for other entities
				if(target!=null && target instanceof Player) {
					ItemStack held = ((Player) target).getItemInHand();
					((Player) target).setItemInHand(null);
					if(held.getType()!=Material.AIR) {
						target.getWorld().dropItemNaturally(target.getLocation(), held);
						
						return RETURN_TRUE;
					}
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
