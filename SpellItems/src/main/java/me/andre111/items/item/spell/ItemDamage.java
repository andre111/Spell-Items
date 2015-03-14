package me.andre111.items.item.spell;

import java.util.UUID;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemDamage extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue playerN = LUAHelper.getInternalValue(args.arg(1));
			LuaValue targetN = LUAHelper.getInternalValue(args.arg(2));
			LuaValue damageN = args.arg(3);
			
			if(playerN.isuserdata(UUID.class) && targetN.isuserdata(UUID.class) && damageN.isnumber()) {
				Entity player = EntityHandler.getEntityFromUUID((UUID) playerN.touserdata(UUID.class));
				Entity target = EntityHandler.getEntityFromUUID((UUID) targetN.touserdata(UUID.class));
				double damage = damageN.todouble();
				
				if(player!=null && target!=null && player instanceof LivingEntity && target instanceof LivingEntity) {
					if(castIntern((LivingEntity) player, (LivingEntity) target, damage))
						return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private boolean castIntern(LivingEntity player, LivingEntity target, double damage) {
		if(damage>0) {
			target.damage(damage, player);
		} else {
			double newHealth = target.getHealth() - damage;
			if(newHealth>target.getMaxHealth()) newHealth = target.getMaxHealth();
			
			target.setHealth(newHealth);
		}
		
		return true;
	}
}