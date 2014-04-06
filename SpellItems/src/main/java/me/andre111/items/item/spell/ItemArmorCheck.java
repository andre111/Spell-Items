package me.andre111.items.item.spell;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;

import org.bukkit.Bukkit;
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
				Player player = Bukkit.getPlayerExact(playerN.toString());
				boolean shouldHave = armorN.toboolean();
				
				if(player!=null) {
					if(ItemHandler.isArmorEmpty(player)) {
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
