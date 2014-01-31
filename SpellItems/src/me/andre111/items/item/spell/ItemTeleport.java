package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemTeleport extends ItemSpell {
	private String playername = "";
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
	}
}
