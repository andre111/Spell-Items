package me.andre111.items.item.spell;

import me.andre111.items.ItemHandler;
import me.andre111.items.item.ItemSpell;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemGetItem extends ItemSpell {
	private String item = "";
	private int times = 20;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==1) times = (int) Math.round(var);
	}
	@Override
	public void setCastVar(int id, String var) {
		if(id==0) item = var;
	}
	
	@Override
	public boolean cast(Player player) {	
		PlayerInventory inv = player.getInventory();
		for(int i=0; i<times; i++) {
			ItemStack it = ItemHandler.decodeItem(item);
			if(it!=null)
				inv.addItem(it);
		}
		
		ItemHandler.updateInventory(player);
		
		return false;
	}
	
	@Override
	public boolean cast(Player player, Block block) {
		return cast(player);
	}
	@Override
	public boolean cast(Player player, Player target) {
		return cast(player);
	}
	@Override
	public boolean cast(Player player, Location loc) {
		return cast(player);
	}
}
