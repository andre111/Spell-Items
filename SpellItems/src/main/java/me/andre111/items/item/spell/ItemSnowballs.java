package me.andre111.items.item.spell;

import java.util.Random;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.EntityHandler;
import me.andre111.items.volatileCode.UnsafeMethods;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemSnowballs extends ItemSpell {
	/*private int needed = 96;
	private String needS = "You need 96 Snowballs!";
	private boolean isReset = true;*/
	
	private static float identifier = (float)Math.random() * 20F;
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue playerN = args.arg(1);
			LuaValue neededN = args.arg(2);
			LuaValue needSN = args.arg(3);
			
			if(playerN.isstring() && neededN.isnumber() && needSN.isstring()) {
				Entity target = EntityHandler.getEntityFromUUID(playerN.toString());
				int needed = neededN.toint();
				String needS = needSN.toString();
				
				if(target!=null && target instanceof Player) {
					Player p = (Player) target;
					if(ItemHandler.countItems(p, Material.SNOW_BALL, 0)>=needed) {
						ItemHandler.removeItems(p, Material.SNOW_BALL, 0, needed);

						Random rand = new Random();
						Vector mod;
						for (int i = 0; i < 250; i++) {
							Snowball snowball = p.launchProjectile(Snowball.class);
							snowball.setFallDistance(identifier); // tag the snowballs
							mod = new Vector((rand.nextDouble() - .5) * 15 / 10.0, (rand.nextDouble() - .5) * 5 / 10.0, (rand.nextDouble() - .5) * 15 / 10.0);
							snowball.setVelocity(snowball.getVelocity().add(mod));
						}

						UnsafeMethods.updateInventory(p);
						
						return RETURN_TRUE;
					} else {
						target.sendMessage(needS);
					}
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
