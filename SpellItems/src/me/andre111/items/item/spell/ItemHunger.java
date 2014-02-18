package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemHunger extends ItemSpell {
	/*private int ammount = 2;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue ammountN = args.arg(2);
			
			if(playerN.isstring() && ammountN.isnumber()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				int ammount = ammountN.toint();
				
				if(player!=null) {
					int newfood = player.getFoodLevel()-ammount;
					if(newfood<0) newfood = 0;
					
					player.setFoodLevel(newfood);
					
					//über 50 - alles entfernen
					if(ammount>50) {
						player.setFoodLevel(0);
						player.setSaturation(0);
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
