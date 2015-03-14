package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.world.WorldThunderStorm;

import org.bukkit.Location;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemWorldThunderStorm extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=4) {
			LuaValue locN = LUAHelper.getInternalValue(args.arg(1));
			LuaValue timeN = args.arg(2);
			LuaValue chanceN = args.arg(3);
			LuaValue distanceN = args.arg(4);
			
			if(locN.isuserdata(Location.class) && timeN.isnumber() && chanceN.isnumber() && distanceN.isnumber()) {
				Location loc = (Location) locN.touserdata(Location.class);
				int time = timeN.toint();
				int chance = chanceN.toint();
				int distance = distanceN.toint();
				
				if(loc!=null) {
					castIntern(loc, time, chance, distance);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private boolean castIntern(Location loc, int time, int chance, int distance) {
		WorldThunderStorm effect = new WorldThunderStorm(chance, distance);
		effect.start(loc.getWorld(), loc, time);
		
		return true;
	}
}
