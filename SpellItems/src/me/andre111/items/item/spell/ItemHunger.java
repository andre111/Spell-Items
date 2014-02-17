package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemHunger extends ItemSpell {
	/*private int ammount = 2;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) ammount = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) ammount = var.getAsInt();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		if(target==null) {
			if(player!=null) resetCoolDown(player);
			return false;
		}
		
		int newfood = target.getFoodLevel()-ammount;
		if(newfood<0) newfood = 0;
		
		target.setFoodLevel(newfood);
		
		//über 50 - alles entfernen
		if(ammount>50) {
			target.setFoodLevel(0);
			target.setSaturation(0);
		}
		
		return true;
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue ammountN = args.arg(2);
			
			if(playerN.isstring() && ammountN.isnumber()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				int ammount = ammountN.toint();
				
				if(player!=null) {
					int newfood = player.getFoodLevel()-ammount;
					if(newfood<0) newfood = 0;
					
					player.setFoodLevel(newfood);
					
					//über 50 - alles entfernen
					if(ammount>50) {
						player.setFoodLevel(0);
						player.setSaturation(0);
					}
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
