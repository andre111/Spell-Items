package me.andre111.items.item.effect;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemEffect;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemEffectNormal extends ItemEffect {
	private String eName = "";
	private int eData = 0;

	@Override
	public void setVars(String vars) {
		String[] split = vars.split(":");
		
		if(split.length>0) eName = split[0];
		if(split.length>1) eData = Integer.parseInt(split[1]);
	}

	@Override
	public void play(Location loc) {
		if(!eName.equals("")) {
			loc.getWorld().playEffect(loc, Effect.valueOf(eName), eData);
		}
	}
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue locationN = args.arg(1);
			
			//Get Location
			Location loc = null;
			if(locationN.isuserdata(Location.class)) {
				loc = (Location) locationN.touserdata(Location.class);
			} else if(locationN.isuserdata(Block.class)) {
				loc = ((Block) locationN.touserdata(Block.class)).getLocation();
			} else if(locationN.isstring()) {
				Entity target = EntityHandler.getEntityFromUUID(locationN.toString());
				if(target!=null) {
					loc = target.getLocation();
				}
			}
			
			if(args.narg()>=2 && args.arg(2).isstring()) {
				eName = args.arg(2).toString();
			}
			if(args.narg()>=3 && args.arg(3).isnumber()) {
				eData = args.arg(3).toint();
			}
			
			play(loc);
			
			return RETURN_TRUE;
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
