package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemExperience extends ItemSpell {
	private boolean self = true;
	private int amount = 3;

	@Override
	public void setCastVar(int id, double var) {
		if(id==0) self = var==1;
		if(id==1) amount = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) self = var.getAsIntBoolean();
		if(id==1) amount = var.getAsInt();
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
			pTarget.giveExp(amount);
			return true;
		}
		
		return false;
	}
}
