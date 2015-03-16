package me.andre111.items.item.effect;

import me.andre111.items.SpellItems;
import me.andre111.items.lua.LUAHelper;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class LuaItemEffectNormal extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue locationN = LUAHelper.getInternalValue(args.arg(1));
			
			//Get Location
			Location loc = null;
			String eName = "";
			int eData = 0;
			
			if(locationN.isuserdata(Location.class)) {
				loc = (Location) locationN.touserdata(Location.class);
			}
			if(args.narg()>=2 && args.arg(2).isstring()) {
				eName = args.arg(2).toString();
			}
			if(args.narg()>=3 && args.arg(3).isnumber()) {
				eData = args.arg(3).toint();
			}
			
			if(loc!=null && !eName.equals("")) {
				loc.getWorld().playEffect(loc, Effect.valueOf(eName), eData);
				
				return LuaValue.TRUE;
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return LuaValue.FALSE;
	}
}
