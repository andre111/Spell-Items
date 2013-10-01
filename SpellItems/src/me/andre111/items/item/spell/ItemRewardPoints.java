package me.andre111.items.item.spell;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.andre111.items.RewardManager;
import me.andre111.items.item.ItemSpell;

public class ItemRewardPoints extends ItemSpell {
	private boolean self = true;
	private int points = 1;

	@Override
	public void setCastVar(int id, double var) {
		if(id==0) self = var==1;
		else if(id==1) points = (int) Math.round(var);
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Player pTarget = null;
		if(self) {
			pTarget = player;
		} else if(target!=null) {
			pTarget = target;
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
	}
}
