package me.andre111.items;

import java.util.Random;

import me.andre111.items.item.enchant.CustomEnchant;
import me.andre111.items.volatileCode.DeprecatedMethods;
import me.andre111.items.volatileCode.DynamicClassFunctions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemHandler {
	private static final Random random = new Random();
	
	public static ItemStack decodeItem(String str, Player player) {
		while(str.startsWith(" ")) {
			str = str.substring(1);
		}
		while(str.endsWith(" ")) {
			str = str.substring(0, str.length()-1);
		}
		
		if(str.startsWith("!")) {
			return decodeNewItem(str.substring(1), player);
		} else {
			return decodeNewItem(str, player);
		}
	}
	
	private static ItemStack decodeNewItem(String str, Player player) {
		ItemStack item = null;
		Material material = null;
		int damage = 0;
		int countmin = 1;
		int countmax = -1;
		double chance = 100;
		String permission = ".";
		String dataTag = "";
		
		boolean exception = false;
		
		String[] geteilt = str.split(" ");
		try {
			//Item Material or Special Item
			if(geteilt.length>0) {
				if(geteilt[0].toLowerCase().startsWith("minecraft:")) {
					material = Material.matchMaterial(geteilt[0]);
	
					if (material == null) {
						material = DeprecatedMethods.getMaterialFromInternalName(geteilt[0]);
					}
				} else if(geteilt[0].toLowerCase().startsWith("spellitems:")) {
					String[] iname = geteilt[0].split(":");
					item = SpellItems.itemManager.getItemStackByName(iname[1]);
				}
			}
			//damage 
			if(geteilt.length>1) {
				damage = Integer.parseInt(geteilt[1]);
			}
			//count
			if(geteilt.length>2) {
				String[] counts = geteilt[2].split(":");
				if(counts.length>0) countmin = Integer.parseInt(counts[0]);
				if(counts.length>1) countmax = Integer.parseInt(counts[1]);
			}
			//chance
			if(geteilt.length>3) {
				chance = Double.parseDouble(geteilt[3]);
				if(!(random.nextDouble()*100<chance)) return null;
			}
			//permission
			if(geteilt.length>5) {
				permission = geteilt[5];
				if(!permission.equals(".")) {
					if(player==null) return null;
					if(!player.hasPermission(permission)) return null;
				}
			}
			//dataTag
			if(geteilt.length>6) {
				for(int i=6; geteilt.length>i; i++) {
					if(dataTag.equals("")) {
						dataTag = geteilt[i];
					} else {
						dataTag = dataTag + " " + geteilt[i];
					}
				}
			}
			
			//item erstellen
			int count = countmin;
			if(countmax!=-1) count = countmin + random.nextInt(countmax-countmin+1);
			if(material!=null || item==null) {
				item = new ItemStack(material, count, (short) damage);
			} else if(item!=null) {
				item.setAmount(count);
			}
			
			//dataTag einfügen
			if(!dataTag.equals("")) {
				try {
					item = DeprecatedMethods.modifyItemStack(item, dataTag);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			//custom enchantments
			if(geteilt.length>4) {
				boolean addGlow = false;
				String[] enchants = geteilt[4].split(",");
				for(int i=0; i<enchants.length; i++) {
					String[] split_e = enchants[i].split(":");
					int elevel = 0;
	
					if(split_e.length>0) {
						if(split_e.length>1) elevel = Integer.parseInt(split_e[1]);
		
						if(split_e[0].equals("-1")) {
							
						} else if(split_e[0].equals("-10")) {
							addGlow = true;
						} else {
							CustomEnchant ce = SpellItems.enchantManager.getEnchantmentByName(split_e[0]);
							if(ce!=null) {
								item = ce.enchantItem(item, elevel);
							}
						}
					}
				}
				
				if(addGlow) {
					item = DynamicClassFunctions.addGlow(item);
				}
			}
			
			return item;
		} catch (NumberFormatException e) {
			exception = true;
		}
		
		if((item==null && !str.equals("0")) || exception) { 
			SpellItems.log("Could not decode Itemstring: "+str);
		}
		return null;
	}
	
	//TODO - recode for new Itemformat Version
	/*public static int decodeItemId(String str) {
		while(str.startsWith(" ")) {
			str = str.substring(1);
		}
		while(str.endsWith(" ")) {
			str = str.substring(0, str.length()-1);
		}
		
		int id = -1;
		String[] geteilt = str.split(" ");
		//id
		if(geteilt.length>0) {
			String[] id_d = geteilt[0].split(":");
			if(id_d.length>0) id = Integer.parseInt(id_d[0]); {
				try {
					id = Integer.parseInt(id_d[0]);
				} catch (NumberFormatException e) {
					//custom items
					if(SpellItems.itemManager.getItemStackByName(id_d[0])!=null)
						id = SpellItems.itemManager.getItemStackByName(id_d[0]).getTypeId();
				}
			}
		}
		
		return id;
	}*/
	
	public static void clearInv(Player player, boolean enderChest) {
		PlayerInventory inv = player.getInventory();
		inv.clear();
		inv.clear(inv.getSize() + 0);
		inv.clear(inv.getSize() + 1);
		inv.clear(inv.getSize() + 2);
		inv.clear(inv.getSize() + 3);
		if(enderChest)
			player.getEnderChest().clear();
	}
	
	public static boolean isInvEmpty(Player player, boolean enderChest) {
		for(ItemStack item : player.getInventory().getContents())
		{
		    if(item != null)
		    if(item.getAmount()>0)
		      return false;
		}
		for(ItemStack item : player.getInventory().getArmorContents())
		{
		    if(item != null)
		    if(item.getAmount()>0)
		      return false;
		}
		if(enderChest) {
			for(ItemStack item : player.getEnderChest().getContents())
			{
			    if(item != null)
			    if(item.getAmount()>0)
			      return false;
			}
		}
		
		return true;
	}
	
	public static boolean isArmorEmpty(Player player) {
		for(ItemStack item : player.getInventory().getArmorContents())
		{
		    if(item != null)
		    if(item.getAmount()>0)
		      return false;
		}
		
		return true;
	}
	
	//###################################
	//Inventory Helpers
	//###################################
	public static int removeItems(Player player, Material type, int data, int remaining) {
		int itemsExchanged = 0;
		for (ItemStack i : player.getInventory()){
			if (i != null && i.getType() == type && DeprecatedMethods.getDatavalue(i.getData()) == data){
				if (i.getAmount() > remaining){
					i.setAmount(i.getAmount() - remaining);
					itemsExchanged += remaining;
					remaining = 0;
				}else{
					itemsExchanged += i.getAmount();
					remaining -= i.getAmount();
					player.getInventory().remove(i);
				}
				if(remaining==0) break;
			}
		}
		return itemsExchanged;
	}

	public static int countItems(Player player, Material type, int data) {
		int items = 0;
		for (ItemStack i : player.getInventory()){
			if (i != null && i.getType() == type && DeprecatedMethods.getDatavalue(i.getData()) == data){
				items += i.getAmount();
			}
		}
		return items;
	}
}
