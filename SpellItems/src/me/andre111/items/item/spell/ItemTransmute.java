package me.andre111.items.item.spell;

import java.util.ArrayList;

import me.andre111.items.ItemHandler;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.ItemVariableHelper;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemTransmute extends ItemSpell {
	private int iid = 0;
	private int data = 0;
	private int ammount = 0;
	private String failNeed = "";
	private boolean addToInv = false;
	private ArrayList<String> items = new ArrayList<String>();

	@Override
	public void setCastVar(int id, double var) {
		if(id==0) iid = (int) Math.round(var);
		else if(id==1) data = (int) Math.round(var);
		else if(id==2) ammount = (int) Math.round(var);
		else if(id==4) addToInv = var==1;
	}
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==3) failNeed = var;
		if(id>4) items.add(var);
	}
	
	@Override
	public void setCastVar(int id, Object var) {
		if(id==0) iid = ItemVariableHelper.getVariableAsInt(var);
		else if(id==1) data = ItemVariableHelper.getVariableAsInt(var);
		else if(id==2) ammount = ItemVariableHelper.getVariableAsInt(var);
		else if(id==3) failNeed = ItemVariableHelper.getVariableAsString(var);
		else if(id==4) addToInv = ItemVariableHelper.getVariableAndIntegerBoolean(var);
		else if(id>4) items.add(ItemVariableHelper.getVariableAsString(var));
	}
	
	@Override
	public boolean cast(Player player, Location location, Player target, Block block) {
		if(player==null) return false;
		
		if(ItemHandler.countItems(player, iid, data)>=ammount || ammount==0) {
			if(ammount!=0)
				ItemHandler.removeItems(player, iid, data, ammount);

			World w = player.getWorld();
			Location loc = player.getLocation();
			PlayerInventory inv = player.getInventory();
			
			for(String st : items) {
				ItemStack it = ItemHandler.decodeItem(st);

				if(it!=null) {
					if(addToInv) {
						inv.addItem(it);
					} else {
						w.dropItem(loc, it);
					}
				}
			}
			
			ItemHandler.updateInventory(player);
			
			return true;
		} else {
			if(!failNeed.equals(""))
				player.sendMessage(failNeed);
			
			resetCoolDown(player);
		}
		return false;
	}
}
