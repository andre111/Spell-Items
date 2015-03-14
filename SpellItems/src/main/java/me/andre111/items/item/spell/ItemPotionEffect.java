package me.andre111.items.item.spell;

import java.util.ArrayList;
import java.util.UUID;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;
import me.andre111.items.volatileCode.DeprecatedMethods;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemPotionEffect extends ItemSpell {
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue playerN = LUAHelper.getInternalValue(args.arg(1));
			
			if(playerN.isuserdata(UUID.class)) {
				Entity target = EntityHandler.getEntityFromUUID((UUID) playerN.touserdata(UUID.class));

				if(target!=null && target instanceof LivingEntity) {
					ArrayList<String> effects = new ArrayList<String>();
					
					int pos = 2;
					while(args.narg()>=pos) {
						if(args.arg(pos).isstring()) effects.add(args.arg(pos).toString());
						
						pos += 1;
					}
					
					addEffects((LivingEntity) target, effects);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private void addEffects(LivingEntity player, ArrayList<String> effects) {
		for(String st : effects) {
			String[] split = st.split(":");
			
			int id = Integer.parseInt(split[0]);
			int duration = Integer.parseInt(split[1]);
			int level = 0;
			if(split.length>2) level = Integer.parseInt(split[2]);
			
			PotionEffectType type = DeprecatedMethods.getPotionEffectByID(id);
			if(!EntityHandler.hasHigherPotionEffect(player, type, level)) {
				player.addPotionEffect(new PotionEffect(type, duration, level), true);
			}
		}
	}
}
