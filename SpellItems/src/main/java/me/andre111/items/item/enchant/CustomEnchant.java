package me.andre111.items.item.enchant;

import java.util.ArrayList;
import java.util.List;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemEffect;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.LuaSpell;
import me.andre111.items.utils.Attributes;
import me.andre111.items.utils.Attributes.Attribute;
import me.andre111.items.utils.Attributes.AttributeType;
import me.andre111.items.volatileCode.DynamicClassFunctions;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomEnchant extends LuaSpell {
	private String internalName;
	
	private int id;
	private String name;
	
	private ArrayList<ItemEffect> effects = new ArrayList<ItemEffect>();
	private ItemSpell[] casts;
	
	private String lua;
	
	
	public void applyToPlayer(Player player) {
		
	}

	public ItemStack enchantItem(ItemStack it, int level) {
		ItemMeta im = it.getItemMeta();
		List<String> st = im.getLore();
		
		if(st==null) st = new ArrayList<String>();
		st.add(0, ChatColor.GRAY+getName()+" "+getLevelName(level));
		
		im.setLore(st);
		it.setItemMeta(im);
		
		
		String currentEnchants = "";
		
		Attributes attributes = new Attributes(it);
		for(Attribute att : attributes.values()) {
			if(att.getUUID().equals(SpellItems.itemEnchantUUID)) {
				if(!att.getName().equals("")) currentEnchants = att.getName().replace("si_customenchant_", "");
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
			return ""+level;
		}
	}
	
	public void cast(Player player, Player target, int enchantLevel, double damage) {
		/*if(casts != null) {
			boolean[] states = new boolean[casts.length];
			HashMap<Integer, SpellVariable> variables = new HashMap<Integer, SpellVariable>();
			
			int pos = 0;
			for(ItemSpell castUse : casts) {
				if(castUse != null) {
					states[pos] = castUse.cast(player, null, target, null, states, variables);
					
					createEffects(target.getLocation(), "Target");
					createEffects(player.getLocation(), "Caster");
				}
				
				pos += 1;
			}
		}*/
		
		if(!lua.equals("")) {
			if(player==null) return;

			
			SpellItems.luacontroller.castFunction(this, lua, player.getUniqueId().toString(), target.getUniqueId().toString(), null, null, enchantLevel, damage);
		}
	}
	
	public void createEffects(Location loc, String position) {
		//effects
		for(ItemEffect st : effects) {
			if(st!=null)
			if(st.getLocation().equals(position))
				st.play(loc);
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
	public void addEffect(ItemEffect effect) {
		effects.add(effect);
	}
	public void setSize(int size) {
		casts = new ItemSpell[size];
	}
	public ItemSpell getCast(int pos) {
		return casts[pos];
	}
	public void setCast(ItemSpell cast, int pos) {
		this.casts[pos] = cast;
	}
	public String getLua() {
		return lua;
	}
	public void setLua(String lua) {
		this.lua = lua;
	}
}
