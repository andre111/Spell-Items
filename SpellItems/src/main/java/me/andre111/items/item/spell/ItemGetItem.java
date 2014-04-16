package me.andre111.items.item.spell;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.PlayerHandler;
import me.andre111.items.volatileCode.UnsafeMethods;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemGetItem extends ItemSpell {
	/*private String item = "";
	private int times = 20;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue playerN = args.arg(1);
			LuaValue itemN = args.arg(2);
			LuaValue timesN = args.arg(3);
			
			if(playerN.isstring() && itemN.isstring() && timesN.isnumber()) {
				Player player = PlayerHandler.getPlayerFromUUID(playerN.toString());
				String item = itemN.toString();
				int times = timesN.toint();
				
				if(player!=null) {
					PlayerInventory inv = player.getInventory();
					for(int i=0; i<times; i++) {
						ItemStack it = ItemHandler.decodeItem(item, player);
						if(it!=null)
							inv.addItem(it);
					}
					
					UnsafeMethods.updateInventory(player);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
