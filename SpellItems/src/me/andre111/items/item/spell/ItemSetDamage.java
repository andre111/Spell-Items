package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemSetDamage extends ItemSpell {
	private int damage = 0;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) damage = (int) Math.round(var);
	}
	
	@Override
	public boolean cast(Player player) {
		ItemStack it = player.getItemInHand();
		
		it.setDurability((short) damage);
		
		player.setItemInHand(it);
		
		return true;
	}
	
	@Override
	public boolean cast(Player player, Block target) {
		return cast(player);
	}
	@Override
	public boolean cast(Player player, Player target) {
		return cast(player);
	}
	
	@Override
	public boolean cast(Player player, Location loc) {
		return false;
	}
}
