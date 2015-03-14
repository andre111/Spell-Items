package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.volatileCode.DeprecatedMethods;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemReplace extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=6) {
			LuaValue locN = LUAHelper.getInternalValue(args.arg(1));
			LuaValue rangeN = args.arg(2);
			LuaValue originalIDN = args.arg(3);
			LuaValue originalDamageN = args.arg(2);
			LuaValue replaceIDN = args.arg(3);
			LuaValue replaceDamageN = args.arg(2);
			
			if(locN.isuserdata(Location.class) && rangeN.isnumber() && originalIDN.isnumber() && originalDamageN.isnumber() && replaceIDN.isnumber() && replaceDamageN.isnumber()) {
				Location loc = (Location) locN.touserdata(Location.class);
				
				if(loc!=null) {
					int range = rangeN.toint();
					int originalID = originalIDN.toint();
					int originalDamage = originalDamageN.toint();
					int replaceID = replaceIDN.toint();
					int replaceDamage = replaceDamageN.toint();
					
					replaceNear(loc, range, originalID, originalDamage, replaceID, replaceDamage);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private boolean replaceNear(Location loc, int range, int originalID, int originalDamage, int replaceID, int replaceDamage) {
		boolean replaced = false;
		
		for(int xx=-range; xx<=range; xx++) {
			for(int yy=-range; yy<=range; yy++) {
				for(int zz=-range; zz<=range; zz++) {
					Block block = loc.getWorld().getBlockAt(loc.getBlockX()+xx, loc.getBlockY()+yy, loc.getBlockZ()+zz);
					
					if(DeprecatedMethods.getBlockID(block)==originalID && DeprecatedMethods.getBlockData(block)==originalDamage) {
						DeprecatedMethods.setBlockIDandData(block, replaceID, (byte) replaceDamage);
					}
				}
			}
		}
		
		return replaced;
	}
}
