package me.andre111.items.item.effect;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemEffect;
import me.andre111.items.utils.PlayerHandler;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemEffectLightning extends ItemEffect {
	
	@Override
	public void play(Location loc) {
		loc.getWorld().strikeLightningEffect(loc);
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
				Player player = PlayerHandler.getPlayerFromUUID(locationN.toString());
				if(player!=null) {
					loc = player.getLocation();
				}
			}
			
			play(loc);
			
			return RETURN_TRUE;
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
