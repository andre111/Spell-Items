package me.andre111.items.item.effect;

import me.andre111.items.SpellItems;
import me.andre111.items.lua.LUAHelper;

import org.bukkit.Location;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class LuaItemEffectLightning extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue locationN = LUAHelper.getInternalValue(args.arg(1));
			
			//Get Location
			Location loc = null;
			if(locationN.isuserdata(Location.class)) {
				loc = (Location) locationN.touserdata(Location.class);
			}
			
			if(loc!=null) {
				loc.getWorld().strikeLightningEffect(loc);
				
				return LuaValue.TRUE;
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return LuaValue.FALSE;
	}
}
