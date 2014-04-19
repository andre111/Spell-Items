package me.andre111.items.item.enchant;

import java.util.List;
import java.util.Set;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemManager;
import me.andre111.items.utils.AttributeStorage;

import org.bukkit.Material;
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

		enchants[enchantCounter] = enTemp;
		enchantCounter++;
	}
	
	//cast an enchantment
	private void castOn(Player attacker, Player player, CustomEnchant ce) {
		ce.cast(attacker, player);
	}
	
	//get enchantments from item
	public void attackPlayerByPlayer(Player attacker, Player player, ItemStack it) {
		if(it==null) return;
		if(it.getType()==Material.AIR) return;
		
		AttributeStorage storage = AttributeStorage.newTarget(it, SpellItems.itemUUID);
		if(!storage.getData("").startsWith("si_customenchant_")) return;
		String enchants = storage.getData("").replace("si_customenchant_", "");
		
		for(String st : enchants.split("|")) {
			String[] info = st.split(":");
			CustomEnchant ce = getEnchantmentByName(info[0]);
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
	public void projectileShoot(ItemStack it, Projectile a) {
		if(it==null) return;
		
		AttributeStorage storage = AttributeStorage.newTarget(it, SpellItems.itemUUID);
		if(!storage.getData("").startsWith("si_customenchant_")) return;
		String enchants = storage.getData("").replace("si_customenchant_", "");
		
		int pos = 0;
		for(String st : enchants.split("|")) {
			String[] info = st.split(":");
			CustomEnchant ce = getEnchantmentByName(info[0]);
			if(ce!=null) {
				a.setMetadata("spellitems_enchant_"+pos, new FixedMetadataValue(SpellItems.instance, ce.getInternalName()));
				pos++;
			}
		}
	}
	
	/*public boolean isCustomEnchantment(String lore) {
		if(getEnchantmentByDisplayname(lore)!=null) return true;
		
		return false;
	}*/
	
	//IMPORTANT: This expects an level at the end of the string
	/*public CustomEnchant getEnchantmentByDisplayname(String name) {
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
	}*/
	
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
