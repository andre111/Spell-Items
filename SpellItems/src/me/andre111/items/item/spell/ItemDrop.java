package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemDrop extends ItemSpell {
	
	@Override
	public boolean cast(Player player, Player target) {
		ItemStack held = target.getItemInHand();
		target.setItemInHand(null);
		if(held.getTypeId()!=0) {
			target.getWorld().dropItemNaturally(target.getLocation(), held);
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean cast(Player player) {
		resetCoolDown(player);
		return false;
	}
	@Override
	public boolean cast(Player player, Block block) {
		resetCoolDown(player);
		return false;
	}
	@Override
	public boolean cast(Player player, Location loc) {
		resetCoolDown(player);
		return false;
	}
}
