package me.andre111.items.item.spell;

import java.util.UUID;

import me.andre111.items.RewardManager;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemRewardPoints extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = LUAHelper.getInternalValue(args.arg(1));
			LuaValue pointsN = args.arg(2);
			
			if(playerN.isuserdata(UUID.class) && pointsN.isnumber()) {
				Entity target = EntityHandler.getEntityFromUUID((UUID) playerN.touserdata(UUID.class));
				int points = pointsN.toint();
				
				if(target!=null && target instanceof Player) {
					if(points>0) {
						RewardManager.addRewardPoints((Player) target, points);
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
