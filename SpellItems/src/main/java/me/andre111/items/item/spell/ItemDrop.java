package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.PlayerHandler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemDrop extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue playerN = args.arg(1);
			
			if(playerN.isstring()) {
				Player player = PlayerHandler.getPlayerFromUUID(playerN.toString());
				
				if(player!=null) {
					ItemStack held = player.getItemInHand();
					player.setItemInHand(null);
					if(held.getType()!=Material.AIR) {
						player.getWorld().dropItemNaturally(player.getLocation(), held);
						
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
