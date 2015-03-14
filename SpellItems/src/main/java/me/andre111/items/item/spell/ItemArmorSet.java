package me.andre111.items.item.spell;

import java.util.UUID;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemArmorSet extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue playerN = LUAHelper.getInternalValue(args.arg(1));

			if(playerN.isuserdata(UUID.class)) {
				Entity target = EntityHandler.getEntityFromUUID((UUID) playerN.touserdata(UUID.class));
				
				//TODO - X code for other entities
				if(target!=null && target instanceof Player) {
					String helmet = "";
					String chest = "";
					String leggins = "";
					String boots = "";
					
					if(args.narg()>=2 && args.arg(2).isstring()) helmet = args.arg(2).toString();
					if(args.narg()>=3 && args.arg(3).isstring()) helmet = args.arg(3).toString();
					if(args.narg()>=4 && args.arg(4).isstring()) helmet = args.arg(4).toString();
					if(args.narg()>=5 && args.arg(5).isstring()) helmet = args.arg(5).toString();
					
					setArmor((Player) target, helmet, chest, leggins, boots);
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private void setArmor(Player player, String helmet, String chest, String leggins, String boots) {
		ItemStack helmetIt = ItemHandler.decodeItem(helmet, player);
		if(helmetIt!=null) player.getInventory().setHelmet(helmetIt);
		
		ItemStack chestIt = ItemHandler.decodeItem(chest, player);
		if(chestIt!=null) player.getInventory().setChestplate(chestIt);
		
		ItemStack legginsIt = ItemHandler.decodeItem(leggins, player);
		if(legginsIt!=null) player.getInventory().setLeggings(legginsIt);
		
		ItemStack bootsIt = ItemHandler.decodeItem(boots, player);
		if(bootsIt!=null) player.getInventory().setBoots(bootsIt);
	}
}
