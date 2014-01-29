package me.andre111.items.item.spell;

import me.andre111.items.ItemHandler;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemArmorCheck extends ItemSpell {
	private boolean self = false;
	private boolean shouldHave = false;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) self = (var==1);
		else if(id==1) shouldHave = (var==1);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) self = var.getAsIntBoolean();
		else if(id==1) shouldHave = var.getAsIntBoolean();
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
			if(ItemHandler.isArmorEmpty(pTarget)) {
				if(shouldHave) return false;
			} else {
				if(!shouldHave) return false;
			}
		}
		
		return false;
	}
}
