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

public class ItemExperience extends ItemSpell {
	/*private String playername = "";
	private int amount = 3;

	@Override
	public void setCastVar(int id, String var) {
		if(id==0) playername = var;
	}
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==1) amount = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) playername = var.getAsString();
		if(id==1) amount = var.getAsInt();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Player pTarget = Bukkit.getPlayerExact(playername);
		if(playername.equals("")) {
			pTarget = player;
		}
		
		if(pTarget!=null) {
			pTarget.giveExp(amount);
			return true;
		}
		
		return false;
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
					player.giveExp(ammount);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
