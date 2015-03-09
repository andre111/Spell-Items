package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemDamage extends ItemSpell {
	/*private String playername = "";
	private int damage = 4;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue playerN = args.arg(1);
			LuaValue targetN = args.arg(2);
			LuaValue damageN = args.arg(3);
			
			if(playerN.isstring() && targetN.isstring() && damageN.isnumber()) {
				Entity player = EntityHandler.getEntityFromUUID(playerN.toString());
				Entity target = EntityHandler.getEntityFromUUID(targetN.toString());
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