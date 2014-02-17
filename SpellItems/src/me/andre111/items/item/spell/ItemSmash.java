package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemSmash extends ItemSpell {
	/*private boolean playSound = true;
	private boolean isReset = true;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) playSound = (var==1);
		else if(id==1) isReset = (var==1);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) playSound = var.getAsIntBoolean();
		else if(id==1) isReset = var.getAsIntBoolean();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Location targetLoc = null;
		
		if(block!=null) {
			targetLoc = block.getLocation();
		} else if(loc!=null) {
			targetLoc = loc;
		}
		
		if(targetLoc!=null) {
			World w = targetLoc.getWorld();

			w.createExplosion(targetLoc, 2);
			//that will break anything-removed
			//w.getBlockAt(target).setTypeId(0);
			if(playSound)
				w.playSound(targetLoc, Sound.IRONGOLEM_THROW, 1, 1);
			
			return true;
		}
		
		if(player!=null && isReset) {
			resetCoolDown(player);
		}
		return false;
	}*/
	
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
