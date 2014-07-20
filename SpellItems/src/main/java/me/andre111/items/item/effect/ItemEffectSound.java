package me.andre111.items.item.effect;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemEffect;
import me.andre111.items.utils.PlayerHandler;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemEffectSound extends ItemEffect {
	private String sName = "";
	private float sVolume = 1;
	private float sPitch = 1;

	@Override
	public void setVars(String vars) {
		String[] split = vars.split(":");
		
		if(split.length>0) sName = split[0];
		if(split.length>1) sVolume = Float.parseFloat(split[1]);
		if(split.length>2) sPitch = Float.parseFloat(split[2]);
	}

	@Override
	public void play(Location loc) {
		if(!sName.equals("")) {
			loc.getWorld().playSound(loc, Sound.valueOf(sName), sVolume, sPitch);
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
				Player player = PlayerHandler.getPlayerFromUUID(locationN.toString());
				if(player!=null) {
					loc = player.getLocation();
				}
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
			
			play(loc);
			
			return RETURN_TRUE;
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
