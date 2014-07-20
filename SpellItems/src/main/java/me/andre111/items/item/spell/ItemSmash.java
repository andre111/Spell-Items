package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemSmash extends ItemSpell {
	/*private boolean playSound = true;
	private boolean isReset = true;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue locN = args.arg(1);
			LuaValue playSoundN = args.arg(2);
			
			if(locN.isuserdata(Location.class) && playSoundN.isboolean()) {
				Location targetLoc = (Location) locN.touserdata(Location.class);
				boolean playSound = playSoundN.toboolean();
				
				if(targetLoc!=null) {
					World w = targetLoc.getWorld();

					w.createExplosion(targetLoc, 2);
					//that will break anything-removed
					//w.getBlockAt(target).setTypeId(0);
					if(playSound)
						w.playSound(targetLoc, Sound.IRONGOLEM_THROW, 1, 1);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
