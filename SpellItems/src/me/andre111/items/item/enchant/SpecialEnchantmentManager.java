package me.andre111.items.item.enchant;

import java.util.List;
import java.util.Set;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemManager;
import me.andre111.items.item.ItemSpell;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class SpecialEnchantmentManager {
	private CustomEnchant[] enchants;
	private int enchantCounter;
	
	
	public void loadEnchants(FileConfiguration df) {
		//items
		enchantCounter = 0;
		ConfigurationSection as = df.getConfigurationSection("enchantments");
		Set<String> strings2 = as.getKeys(false);
		String[] stK2 = strings2.toArray(new String[strings2.size()]);
		//load items
		enchants = new CustomEnchant[stK2.length];
		for(int i=0; i<stK2.length; i++) {
			loadEnchant(df, stK2[i]);
		}
	}
	
	public void addEnchants(FileConfiguration df) {
		ConfigurationSection as = df.getConfigurationSection("enchantments");
		Set<String> strings2 = as.getKeys(false);
		String[] stK2 = strings2.toArray(new String[strings2.size()]);
		//load items
		CustomEnchant[] tempEnchants = new CustomEnchant[enchants.length+stK2.length];
		enchantCounter = 0;
		for(CustomEnchant ce : enchants) {
			tempEnchants[enchantCounter] = ce;
			enchantCounter++;
		}
		enchants = tempEnchants;
		for(int i=0; i<stK2.length; i++) {
			loadEnchant(df, stK2[i]);
		}
	}
	
	private void loadEnchant(FileConfiguration df, String en) {
		CustomEnchant enTemp = new CustomEnchant();
		
		enTemp.setInternalName(en);
		enTemp.setName(df.getString("enchantments."+en+".name", ""));
		List<String> effects = df.getStringList("enchantments."+en+".effects");
		if(effects.size()>0)
		for(String st : effects) {
			enTemp.addEffect(ItemManager.getItemEffect(st));
		}
		
		//Cast
		enTemp.setLua(df.getString("enchantments."+en+".lua", ""));
		
		//right
		/*ConfigurationSection as = df.getConfigurationSection("enchantments."+en+".casts");
		if(as!=null) {
			Set<String> strings2 = as.getKeys(false);
			if(strings2.size()>0) {
				String[] casts = strings2.toArray(new String[strings2.size()]);

				enTemp.setSize(casts.length);
				for(int i=0; i<casts.length; i++) {
					loadCast(df, enTemp, en, casts[i], i);
				}
			}
		}*/

		enchants[enchantCounter] = enTemp;
		enchantCounter++;
	}
	
	private void loadCast(FileConfiguration df, CustomEnchant enTemp, String en, String name, int id) {
		String basename = "enchantments."+en+".casts."+name+".";

		//leftclick
		String cast = df.getString(basename+"cast", "");
		try {
			if(!cast.contains("me.andre111.items.item.spell.")) {
				cast = "me.andre111.items.item.spell." + cast;
			}
			Class<?> c = Class.forName(cast);
			if(c.getSuperclass().equals(ItemSpell.class)) {
				enTemp.setCast((ItemSpell) c.newInstance(), id);
				enTemp.getCast(id).setItemName(en);
				enTemp.getCast(id).setAction(10);
				enTemp.getCast(id).setRequire(df.getInt(basename+"require", -1));
				
				//new method, for loading more than 2 cast vars
				List<String> stList = df.getStringList(basename+"castVars");
				ItemSpell itS = enTemp.getCast(id);

				for(int i=0; i<stList.size(); i++) {
					//load vars->set to has var
					if(stList.get(i).startsWith("var:")) {
						try {
							int varid = Integer.parseInt(stList.get(i).replace("var:", ""));
							itS.setVariable(i, varid);
							continue;
						} catch (NumberFormatException  e) {
						}
					}
					//load as normal string/int
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
		if(enTemp.getCast(id)==null) enTemp.setCast(new ItemSpell(), id);
	}
	
	//cast an enchantment
	private void castOn(Player attacker, Player player, CustomEnchant ce) {
		ce.cast(attacker, player);
	}
	
	//get enchantments from item
	public void attackPlayerByPlayer(Player attacker, Player player, ItemStack it) {
		if(it==null) return;
		if(it.getItemMeta()==null) return;
		if(it.getItemMeta().getLore()==null) return;
		
		for(String st : it.getItemMeta().getLore()) {
			CustomEnchant ce = getEnchantmentByDisplayname(st);
			if(ce!=null) {
				castOn(attacker, player, ce);
			}
		}
	}
	
	//get enchantments from arrow
	public void attackPlayerByProjectile(Player attacker, Player player, Projectile a) {
		int pos = 0;
		while(!a.getMetadata("spellitems_enchant_"+pos).isEmpty()) {
			String ench = a.getMetadata("spellitems_enchant_"+pos).get(0).asString();
			CustomEnchant ce = getEnchantmentByName(ench);
			if(ce!=null) {
				castOn(attacker, player, ce);
			}
			
			pos++;
		}
	}
	
	//save enchants on arrow
	public void procectileShoot(ItemStack bow, Projectile a) {
		if(bow==null) return;
		if(bow.getItemMeta()==null) return;
		if(bow.getItemMeta().getLore()==null) return;
		
		int pos = 0;
		for(String st : bow.getItemMeta().getLore()) {
			CustomEnchant ce = getEnchantmentByDisplayname(st);
			if(ce!=null) {
				a.setMetadata("spellitems_enchant_"+pos, new FixedMetadataValue(SpellItems.instance, ce.getInternalName()));
				pos++;
			}
		}
	}
	
	public boolean isCustomEnchantment(String lore) {
		if(getEnchantmentByDisplayname(lore)!=null) return true;
		
		return false;
	}
	
	//IMPORTANT: This expects an level at the end of the string
	public CustomEnchant getEnchantmentByDisplayname(String name) {
		String[] split = name.split(" ");
		String putTogether = "";
		for(int i=0; i<split.length-1; i++) {
			if(putTogether.equals("")) putTogether = split[i];
			else putTogether = putTogether + " " + split[i];
		}
		putTogether = putTogether.replace(ChatColor.GRAY+"", "");
		
		for(int i=0; i<enchants.length; i++) {
			if(putTogether.equals(enchants[i].getName())) {
				return enchants[i];
			}
		}
		
		return null;
	}
	
	public CustomEnchant getEnchantmentByName(String name) {
		for(int i=0; i<enchants.length; i++) {
			if(name.equals(enchants[i].getInternalName())) {
				return enchants[i];
			}
		}
		
		return null;
	}
	
	//reload this configsection/file
	public void reload(FileConfiguration df) {
		loadEnchants(df);
	}
}
