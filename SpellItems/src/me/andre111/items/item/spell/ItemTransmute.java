package me.andre111.items.item.spell;

import java.util.ArrayList;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemTransmute extends ItemSpell {
	/*private int iid = 0;
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
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) iid = var.getAsInt();
		else if(id==1) data = var.getAsInt();
		else if(id==2) ammount = var.getAsInt();
		else if(id==3) failNeed = var.getAsString();
		else if(id==4) addToInv = var.getAsIntBoolean();
		else if(id>4) items.add(var.getAsString());
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
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=6) {
			LuaValue playerN = args.arg(1);
			LuaValue iidN = args.arg(2);
			LuaValue dataN = args.arg(3);
			LuaValue ammountN = args.arg(4);
			LuaValue failNeedN = args.arg(5);
			LuaValue addToInvN = args.arg(6);
			
			if(playerN.isstring() && iidN.isnumber() && dataN.isnumber() && ammountN.isnumber() && failNeedN.isstring() && addToInvN.isboolean()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				int iid = iidN.toint();
				int data = dataN.toint();
				int ammount = ammountN.toint();
				String failNeed = failNeedN.toString();
				boolean addToInv = addToInvN.toboolean();
				
				if(player!=null) {
					ArrayList<String> items = new ArrayList<String>();
					int pos = 7;
					while(args.narg()>=pos) {
						if(args.arg(pos).isstring()) items.add(args.arg(pos).toString());
						
						pos++;
					}
					
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
						
						return RETURN_TRUE;
					} else {
						if(!failNeed.equals(""))
							player.sendMessage(failNeed);
					}
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
