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

public class ItemTeleport extends ItemSpell {
	/*private String playername = "";
	private Location targetLoc = null;
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==0) playername = var;
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) playername = var.getAsString();
		else if(id==1) targetLoc = var.getAsLocation();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Player tplayer = Bukkit.getPlayerExact(playername);
		if(playername.equals("")) {
			tplayer = player;
		}
		
		if(tplayer!=null && targetLoc!=null) {
			tplayer.teleport(targetLoc);
			
			return true;
		}
		
		return false;
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue locN = args.arg(2);
			
			if(playerN.isstring() && locN.isuserdata(Location.class)) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				Location targetLoc = (Location) locN.touserdata(Location.class);
				
				if(player!=null && targetLoc!=null) {
					player.teleport(targetLoc);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
