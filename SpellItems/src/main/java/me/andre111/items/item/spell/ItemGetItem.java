package me.andre111.items.item.spell;

import java.util.UUID;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;
import me.andre111.items.volatileCode.DeprecatedMethods;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemGetItem extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue playerN = LUAHelper.getInternalValue(args.arg(1));
			LuaValue itemN = args.arg(2);
			LuaValue timesN = args.arg(3);
			
			if(playerN.isuserdata(UUID.class) && itemN.isstring() && timesN.isnumber()) {
				Entity target = EntityHandler.getEntityFromUUID((UUID) playerN.touserdata(UUID.class));
				String item = itemN.toString();
				int times = timesN.toint();
				
				if(target!=null && target instanceof Player) {
					Player p = (Player) target;
					PlayerInventory inv = p.getInventory();
					for(int i=0; i<times; i++) {
						ItemStack it = ItemHandler.decodeItem(item, p);
						if(it!=null)
							inv.addItem(it);
					}
					
					DeprecatedMethods.updateInventory(p);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
