package me.andre111.items.item.effect;

import me.andre111.items.SpellItems;
import me.andre111.items.lua.LUAHelper;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class LuaItemEffectSound extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue locationN = LUAHelper.getInternalValue(args.arg(1));
			
			//Get Location
			Location loc = null;
			String sName = "";
			float sVolume = 1;
			float sPitch = 1;
			
			if(locationN.isuserdata(Location.class)) {
				loc = (Location) locationN.touserdata(Location.class);
			}
			if(args.narg()>=2 && args.arg(2).isstring()) {
				sName = args.arg(2).toString();
			}
			if(args.narg()>=3 && args.arg(3).isnumber()) {
				sVolume = args.arg(3).tofloat();
			}
			if(args.narg()>=4 && args.arg(4).isnumber()) {
				sPitch = args.arg(4).tofloat();
			}
			
			if(loc!=null && !sName.equals("")) {
				loc.getWorld().playSound(loc, Sound.valueOf(sName), sVolume, sPitch);
				
				return LuaValue.TRUE;
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return LuaValue.FALSE;
	}
}
