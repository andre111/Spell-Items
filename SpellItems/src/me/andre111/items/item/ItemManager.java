package me.andre111.items.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.andre111.items.SpellItems;
import me.andre111.items.utils.AttributeStorage;

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
		itTemp.setID(df.getInt("items."+it+".id", 0));
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
		List<String> effects = df.getStringList("items."+it+".rightclick.effects");
		if(effects.size()>0)
		for(String st : effects) {
			itTemp.addEffectR(getItemEffect(st));
		}
		itTemp.setCooldownR(df.getInt("items."+it+".rightclick.cooldown", 0));
		itTemp.setManaCostR(df.getInt("items."+it+".rightclick.mana.cost", 0));
		//leftclick
		List<String> effectsl = df.getStringList("items."+it+".leftclick.effects");
		if(effectsl.size()>0)
		for(String st : effectsl) {
			itTemp.addEffectL(getItemEffect(st));
		}
		itTemp.setCooldownL(df.getInt("items."+it+".leftclick.cooldown", 0));
		itTemp.setManaCostL(df.getInt("items."+it+".leftclick.mana.cost", 0));
		//eat
		List<String> effectsEat = df.getStringList("items."+it+".onEat.effects");
		if(effectsEat.size()>0)
			for(String st : effectsEat) {
				itTemp.addEffectEat(getItemEffect(st));
			}
		itTemp.setCooldownEat(df.getInt("items."+it+".onEat.cooldown", 0));
		itTemp.setManaCostEat(df.getInt("items."+it+".onEat.mana.cost", 0));
		
		//Cast
		//right
		ConfigurationSection as = df.getConfigurationSection("items."+it+".rightclick.casts");
		if(as!=null) {
			Set<String> strings2 = as.getKeys(false);
			if(strings2.size()>0) {
				String[] castsR = strings2.toArray(new String[strings2.size()]);
				
				itTemp.setSizeR(castsR.length);
				for(int i=0; i<castsR.length; i++) {
					loadCast(df, itTemp, it, castsR[i], 1, i);
				}
			}
		}
		
		//left
		ConfigurationSection asl = df.getConfigurationSection("items."+it+".leftclick.casts");
		if(asl!=null) {
			Set<String> strings = asl.getKeys(false);
			if(strings.size()>0) {
				String[] castsL = strings.toArray(new String[strings.size()]);
				
				itTemp.setSizeL(castsL.length);
				for(int i=0; i<castsL.length; i++) {
					loadCast(df, itTemp, it, castsL[i], 0, i);
				}
			}
		}

		//eat
		ConfigurationSection ase = df.getConfigurationSection("items."+it+".onEat.casts");
		if(ase!=null) {
			Set<String> strings = ase.getKeys(false);
			if(strings.size()>0) {
				String[] castsEat = strings.toArray(new String[strings.size()]);

				itTemp.setSizeEat(castsEat.length);
				for(int i=0; i<castsEat.length; i++) {
					loadCast(df, itTemp, it, castsEat[i], 2, i);
				}
			}
		}
	
		items[itemCounter] = itTemp;
		itemCounter++;
	}
	
	private void loadCast(FileConfiguration df, CustomItem itTemp, String it, String name, int action, int id) {
		String click = "rightclick";
		if(action==0) click = "leftclick";
		if(action==2) click = "onEat";
		String basename = "items."+it+"."+click+".casts."+name+".";

		//leftclick
		String cast = df.getString(basename+"cast", "");
		try {
			if(!cast.contains("me.andre111.items.item.spell.")) {
				cast = "me.andre111.items.item.spell." + cast;
			}
			Class<?> c = Class.forName(cast);
			if(c.getSuperclass().equals(ItemSpell.class)) {
				if(action==0) {
					itTemp.setCastL((ItemSpell) c.newInstance(), id);
					itTemp.getCastL(id).setItemName(it);
					itTemp.getCastL(id).setAction(0);
					itTemp.getCastL(id).setRequire(df.getInt(basename+"require", -1));
				} else if(action==1) {
					itTemp.setCastR((ItemSpell) c.newInstance(), id);
					itTemp.getCastR(id).setItemName(it);
					itTemp.getCastR(id).setAction(action);
					itTemp.getCastR(id).setRequire(df.getInt(basename+"require", -1));
				} else {
					itTemp.setCastEat((ItemSpell) c.newInstance(), id);
					itTemp.getCastEat(id).setItemName(it);
					itTemp.getCastEat(id).setAction(action);
					itTemp.getCastEat(id).setRequire(df.getInt(basename+"require", -1));
				}
				//new method, for loading more than 2 cast vars
				List<String> stList = df.getStringList(basename+"castVars");
				ItemSpell itS;
				if(action==0) itS = itTemp.getCastL(id);
				else if(action==1) itS = itTemp.getCastR(id);
				else itS = itTemp.getCastEat(id);

				for(int i=0; i<stList.size(); i++) {
					itS.setCastVar(i, stList.get(i));
					try {
						double d = Double.parseDouble(stList.get(i));
						itS.setCastVar(i, d);
					} catch (NumberFormatException  e) {
					}
				}
				//changed to string reader, because doublelist skips string
				//-> numbers get messed up
			}
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		if(action==0) {
			if(itTemp.getCastL(id)==null) itTemp.setCastL(new ItemSpell(), id);
		} else if(action==1) {
			if(itTemp.getCastR(id)==null) itTemp.setCastR(new ItemSpell(), id);
		} else {
			if(itTemp.getCastEat(id)==null) itTemp.setCastEat(new ItemSpell(), id);
		}
	}
	
	public static ItemEffect getItemEffect(String st) {
		ItemEffect effect = null;
		
		//effect
		String[] split = st.split(" ");
		if(split.length>1) {
			try {
				if(!split[1].contains("me.andre111.items.item.effect.")) {
					split[1] = "me.andre111.items.item.effect." + split[1];
				}
				Class<?> c = Class.forName(split[1]);
				if(c.getSuperclass().equals(ItemEffect.class)) {
					effect = (ItemEffect) c.newInstance();
				}
			} catch (ClassNotFoundException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
		
		//location
		if(split.length>0 && effect!=null)
			effect.setLocation(split[0]);
		//vars
		if(split.length>2 && effect!=null)
			effect.setVars(split[2]);
		
		return effect;
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
		
		AttributeStorage storage = AttributeStorage.newTarget(it, SpellItems.itemUUID);
		if(!storage.getData("").startsWith("si_customitem_")) return itemList;
		String iname = storage.getData("").replace("si_customitem_", "");
		
		for(int i=0; i<items.length; i++) {
			if(iname.equals(items[i].getInternalName())) {
				itemList.add(items[i]);
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
