package me.andre111.items.item.spell;

import java.util.ArrayList;
import java.util.UUID;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;
import me.andre111.items.volatileCode.DeprecatedMethods;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemTransmute extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=6) {
			LuaValue playerN = LUAHelper.getInternalValue(args.arg(1));
			LuaValue iidN = args.arg(2);
			LuaValue dataN = args.arg(3);
			LuaValue ammountN = args.arg(4);
			LuaValue failNeedN = args.arg(5);
			LuaValue addToInvN = args.arg(6);
			
			if(playerN.isuserdata(UUID.class) && iidN.isnumber() && dataN.isnumber() && ammountN.isnumber() && failNeedN.isstring() && addToInvN.isboolean()) {
				Entity target = EntityHandler.getEntityFromUUID((UUID) playerN.touserdata(UUID.class));
				if(!(target instanceof Player)) return RETURN_FALSE;
				
				Player player = (Player) target;
				
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
					
					Material mat = DeprecatedMethods.getMaterialByID(iid);
					if(ItemHandler.countItems(player, mat, data)>=ammount || ammount==0) {
						if(ammount!=0)
							ItemHandler.removeItems(player, mat, data, ammount);

						World w = player.getWorld();
						Location loc = player.getLocation();
						PlayerInventory inv = player.getInventory();
						
						for(String st : items) {
							ItemStack it = ItemHandler.decodeItem(st, player);

							if(it!=null) {
								if(addToInv) {
									inv.addItem(it);
								} else {
									w.dropItem(loc, it);
								}
							}
						}
						
						DeprecatedMethods.updateInventory(player);
						
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
