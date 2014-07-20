package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;

import org.bukkit.Location;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemExplode extends ItemSpell {
	/*private float power = 6F;
	private boolean kill = true;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue powerN = args.arg(1);
			LuaValue locN = args.arg(2);
			
			if(powerN.isnumber() && locN.isuserdata(Location.class)) {
				float power = (float)powerN.todouble();
				Location loc = (Location) locN.touserdata(Location.class);
				
				if(loc!=null) {
					loc.getWorld().createExplosion(loc, power);
					loc.getWorld().createExplosion(loc, power);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
