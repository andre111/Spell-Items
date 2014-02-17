package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemDrop extends ItemSpell {
	/*@Override
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
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue playerN = args.arg(1);
			
			if(playerN.isstring()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				
				if(player!=null) {
					ItemStack held = player.getItemInHand();
					player.setItemInHand(null);
					if(held.getType()!=Material.AIR) {
						player.getWorld().dropItemNaturally(player.getLocation(), held);
						
						return RETURN_TRUE;
					}
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
