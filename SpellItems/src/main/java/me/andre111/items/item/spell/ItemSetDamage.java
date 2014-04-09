package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.PlayerHandler;

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
				Player player = PlayerHandler.getPlayerFromUUID(playerN.toString());
				int damage = damageN.toint();
				
				if(player!=null) {
					ItemStack it = player.getItemInHand();
					it.setDurability((short) damage);
					player.setItemInHand(it);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
