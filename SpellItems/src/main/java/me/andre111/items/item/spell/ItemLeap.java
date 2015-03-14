package me.andre111.items.item.spell;

import java.util.UUID;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemLeap extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=5) {
			LuaValue playerN = LUAHelper.getInternalValue(args.arg(1));
			LuaValue forwardN = args.arg(2);
			LuaValue upwardN = args.arg(3);
			LuaValue powerN = args.arg(4);
			LuaValue disableDamageN = args.arg(5);
			
			if(playerN.isuserdata(UUID.class) && forwardN.isnumber() && upwardN.isnumber() && powerN.isnumber() && disableDamageN.isboolean()) {
				Entity target = EntityHandler.getEntityFromUUID((UUID) playerN.touserdata(UUID.class));
				
				
				if(target!=null) {
					double forward = forwardN.todouble();
					double upward = upwardN.todouble();
					float power = (float) powerN.todouble();
					boolean disableDamage = disableDamageN.toboolean();
					
					spellLeap(target, forward, upward, power, disableDamage);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	public static void spellLeap(Entity target, double forward, double upward, float power, boolean diasableDamage) {
		Vector v = target.getLocation().getDirection();
        v.setY(0).normalize().multiply(forward*power).setY(upward*power);
        target.setVelocity(v);
        if(diasableDamage)
        	SpellItems.jumpingNormal.add(target);
	}
}
