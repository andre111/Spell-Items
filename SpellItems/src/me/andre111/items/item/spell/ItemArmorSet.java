package me.andre111.items.item.spell;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemArmorSet extends ItemSpell {
	/*private String playername = "";
	
	private String helmet = "";
	private String chest = "";
	private String leggins = "";
	private String boots = "";*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue playerN = args.arg(1);

			if(playerN.isstring()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				
				if(player!=null) {
					String helmet = "";
					String chest = "";
					String leggins = "";
					String boots = "";
					
					if(args.narg()>=2 && args.arg(2).isstring()) helmet = args.arg(2).toString();
					if(args.narg()>=3 && args.arg(3).isstring()) helmet = args.arg(3).toString();
					if(args.narg()>=4 && args.arg(4).isstring()) helmet = args.arg(4).toString();
					if(args.narg()>=5 && args.arg(5).isstring()) helmet = args.arg(5).toString();
					
					setArmor(player, helmet, chest, leggins, boots);
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
