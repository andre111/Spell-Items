package me.andre111.items.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.andre111.items.SpellItems;
import me.andre111.items.utils.Attributes;
import me.andre111.items.utils.Attributes.Attribute;
import me.andre111.items.volatileCode.DeprecatedMethods;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
	private CustomItem[] items;
	private int itemCounter;
	
	public void loadItems(FileConfiguration df) {
		//items
		itemCounter = 0;
		ConfigurationSection as = df.getConfigurationSection("items");
		Set<String> strings2 = as.getKeys(false);
		String[] stK2 = strings2.toArray(new String[strings2.size()]);
		//load items
		items = new CustomItem[stK2.length];
		for(int i=0; i<stK2.length; i++) {
			loadItem(df, stK2[i]);
		}
	}
	
	public void addItems(FileConfiguration df) {
		ConfigurationSection as = df.getConfigurationSection("items");
		Set<String> strings2 = as.getKeys(false);
		String[] stK2 = strings2.toArray(new String[strings2.size()]);
		//load items
		CustomItem[] tempItems = new CustomItem[items.length+stK2.length];
		itemCounter = 0;
		for(CustomItem ci : items) {
			tempItems[itemCounter] = ci;
			itemCounter++;
		}
		items = tempItems;
		for(int i=0; i<stK2.length; i++) {
			loadItem(df, stK2[i]);
		}
	}

	private void loadItem(FileConfiguration df, String it) {
		CustomItem itTemp = new CustomItem();
		itTemp.setInternalName(it);
		itTemp.setName(df.getString("items."+it+".name", ""));
		List<String> lores = df.getStringList("items."+it+".lore");
		for(String st : lores) {
			itTemp.addLore(st);
		}
		Material mat = null;
			int id = df.getInt("items."+it+".id", 0);
			mat = DeprecatedMethods.getMaterialByID(id);
		if(mat==null) return;
		itTemp.setMaterial(mat);
		itTemp.setDamage(df.getInt("items."+it+".data", 0));
		itTemp.setUse(df.getBoolean("items."+it+".useUp", false));
		itTemp.setIgnoreDamage(df.getBoolean("items."+it+".ignoreDamage", false));
		itTemp.setAllowPlace(df.getBoolean("items."+it+".allowPlace", false));
		
		//book
		itTemp.setBookauthor(df.getString("items."+it+".book.author", ""));
		itTemp.setBookpages(df.getStringList("items."+it+".book.pages"));
		
		//countup
		itTemp.setHasCounter(df.getBoolean("items."+it+".countup.enabled", false));
		itTemp.setCounterMax(df.getInt("items."+it+".countup.max", 0));
		itTemp.setCounterStep(df.getInt("items."+it+".countup.perSecond", 0));
		itTemp.setCounterOverridable(df.getBoolean("items."+it+".countup.overridable", true));
		itTemp.setCounterInterruptMove(df.getBoolean("items."+it+".countup.interrupt.move", true));
		itTemp.setCounterInterruptDamage(df.getBoolean("items."+it+".countup.interrupt.damage", true));
		itTemp.setCounterInterruptItem(df.getBoolean("items."+it+".countup.interrupt.itemSwitch", true));
		
		//Rightclick
		itTemp.setCooldownR(df.getInt("items."+it+".rightclick.cooldown", 0));
		itTemp.setManaCostR(df.getInt("items."+it+".rightclick.mana.cost", 0));
		//leftclick
		itTemp.setCooldownL(df.getInt("items."+it+".leftclick.cooldown", 0));
		itTemp.setManaCostL(df.getInt("items."+it+".leftclick.mana.cost", 0));
		//eat
		itTemp.setCooldownEat(df.getInt("items."+it+".onEat.cooldown", 0));
		itTemp.setManaCostEat(df.getInt("items."+it+".onEat.mana.cost", 0));
		
		//Cast
		itTemp.setLuaR(df.getString("items."+it+".rightclick.lua", ""));
		itTemp.setLuaL(df.getString("items."+it+".leftclick.lua", ""));
		itTemp.setLuaEat(df.getString("items."+it+".onEat.lua", ""));
	
		items[itemCounter] = itTemp;
		itemCounter++;
	}
	
	public ItemStack getItemStackByName(String name) {
		for(int i=0; i<items.length; i++) {
			if(name.equals(items[i].getInternalName())) {
				return items[i].getItemStack();
			}
		}
		
		return null;
	}
	public List<CustomItem> getItemByAtrribute(ItemStack it) {
		ArrayList<CustomItem> itemList = new ArrayList<CustomItem>();
		
		if(it.getType()==Material.AIR) return itemList;
		
		
		Attributes attributes = new Attributes(it);
		for(Attribute att : attributes.values()) {
			if(att.getUUID().equals(SpellItems.itemUUID)) {
				if(!att.getName().startsWith("si_customitem_")) return itemList;
				
				String iname = att.getName().replace("si_customitem_", "");
				
				for(int i=0; i<items.length; i++) {
					if(iname.equals(items[i].getInternalName())) {
						itemList.add(items[i]);
					}
				}
			}
		}
		
		return itemList;
	}
	/*public List<CustomItem> getItemByDisplayName(String name) {
		ArrayList<CustomItem> itemList = new ArrayList<CustomItem>();
		
		for(int i=0; i<items.length; i++) {
			if(name.equals(items[i].getName())) {
				itemList.add(items[i]);
			}
		}
		
		return itemList;
	}*/
	public CustomItem getItemByName(String name) {
		for(int i=0; i<items.length; i++) {
			if(name.equals(items[i].getInternalName())) {
				return items[i];
			}
		}
		
		return null;
	}
	
	//reload this configsection/file
	public void reload(FileConfiguration df) {
		loadItems(df);
	}
}
