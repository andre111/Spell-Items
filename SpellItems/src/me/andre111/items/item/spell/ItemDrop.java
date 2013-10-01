package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemDrop extends ItemSpell {
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		if(target==null) {
			if(player!=null) resetCoolDown(player);
			return false;
		}
		
		ItemStack held = target.getItemInHand();
		target.setItemInHand(null);
		if(held.getType()!=Material.AIR) {
			target.getWorld().dropItemNaturally(target.getLocation(), held);
			
			return true;
		}
		
		return false;
	}
}
