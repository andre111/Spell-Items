package me.andre111.items.item.enchant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.andre111.items.SpellItems;
import me.andre111.items.utils.Attributes;
import me.andre111.items.utils.Attributes.Attribute;
import me.andre111.items.utils.Attributes.AttributeType;
import me.andre111.items.volatileCode.DynamicClassFunctions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomEnchant {
	private String internalName;
	
	private int id;
	private String name;
	
	private String lua;
	
	
	public void applyToPlayer(Player player) {
		
	}

	public ItemStack enchantItem(ItemStack it, int level) {
		ItemMeta im = it.getItemMeta();
		List<String> st = im.getLore();
		
		
		
		if(st==null) {
			st = new ArrayList<String>();
		} else {
			//remove old enchantment text
			Iterator<String> iter = st.iterator();
			while(iter.hasNext()) {
				if(iter.next().startsWith(ChatColor.GRAY+getName()+" ")) {
					iter.remove();
				}
			}
		}
		st.add(0, ChatColor.GRAY+getName()+" "+getLevelName(level));
		
		im.setLore(st);
		it.setItemMeta(im);
		
		
		String currentEnchants = "";
		
		Attributes attributes = new Attributes(it);
		for(Attribute att : attributes.values()) {
			if(att.getUUID().equals(SpellItems.itemEnchantUUID)) {
				if(att.getName().startsWith("si_customenchant_")) {
					currentEnchants = att.getName();
					
					//remove enchantment if already present
					if(currentEnchants.contains(getInternalName()+":")) {
						String[] split = currentEnchants.split("\\|");
						currentEnchants = "";
						for(String str : split) {
							if(!str.startsWith(getInternalName()+":")) {
								if(currentEnchants.equals("")) {
									currentEnchants = "si_customenchant_" + str;
								} else {
									currentEnchants = currentEnchants + "|" + str;
								}
								System.out.println("Added "+str);
							} else {
								System.out.println("Remove "+str);
							}
						}
					}
				}
			}
		}
		
		if(!currentEnchants.equals("")) currentEnchants = currentEnchants + "|";
		else currentEnchants = "si_customenchant_";
		
		boolean found = false;
		for(Attribute att : attributes.values()) {
			if(att.getUUID().equals(SpellItems.itemEnchantUUID)) {
				att.setName(currentEnchants+getInternalName()+":"+level);
				found = true;
			}
		}
		
		if(!found) {
			Attribute att = Attribute.newBuilder().uuid(SpellItems.itemEnchantUUID).name(currentEnchants+getInternalName()+":"+level).amount(0).type(AttributeType.GENERIC_ATTACK_DAMAGE).build();

			attributes.add(att);
		}
		
		ItemStack withAttribute = attributes.getStack();
		
		return DynamicClassFunctions.addGlow(withAttribute);
	}
	
	public String getLevelName(int level) {
		switch(level) {
		case 0:
			return "I";
		case 1:
			return "II";
		case 2:
			return "III";
		case 3:
			return "IV";
		case 4:
			return "V";
		case 5:
			return "VI";
		case 6:
			return "VII";
		case 7:
			return "VIII";
		case 8:
			return "IX";
		case 9:
			return "X";
		default:
			return ""+(level+1);
		}
	}
	
	public void cast(Player player, Entity target, int enchantLevel, double damage) {
		if(!lua.equals("")) {
			if(player==null) return;

			
			SpellItems.luacontroller.castFunction(lua, player, target, null, null, enchantLevel, damage);
		}
	}
	
	public String getInternalName() {
		return internalName;
	}
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLua() {
		return lua;
	}
	public void setLua(String lua) {
		this.lua = lua;
	}
}
