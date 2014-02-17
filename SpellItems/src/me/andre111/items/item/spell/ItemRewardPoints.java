package me.andre111.items.item.spell;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import me.andre111.items.RewardManager;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

public class ItemRewardPoints extends ItemSpell {
	/*private String playername = "";
	private int points = 1;

	@Override
	public void setCastVar(int id, String var) {
		if(id==0) playername = var;
	}
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==1) points = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) playername = var.getAsString();
		else if(id==1) points = var.getAsInt();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Player pTarget = Bukkit.getPlayerExact(playername);
		if(playername.equals("")) {
			pTarget = player;
		}
		
		if(pTarget!=null) {
			return castIntern(pTarget);
		}
		
		return false;
	}
	
	private boolean castIntern(Player player) {
		if(points>0) {
			RewardManager.addRewardPoints(player, points);
		}
		
		return true;
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue pointsN = args.arg(2);
			
			if(playerN.isstring() && pointsN.isnumber()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				int points = pointsN.toint();
				
				if(player!=null) {
					if(points>0) {
						RewardManager.addRewardPoints(player, points);
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
