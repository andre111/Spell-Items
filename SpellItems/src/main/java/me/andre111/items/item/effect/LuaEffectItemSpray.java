package me.andre111.items.item.effect;

import java.util.Random;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.lua.LUAHelper;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class LuaEffectItemSpray extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue locationN = LUAHelper.getInternalValue(args.arg(1));
			
			//Get Location
			Location loc = null;
			int number = 1;
			int duration = 6;
			float force = 1.0F; 
			String item = "";
			
			if(locationN.isuserdata(Location.class)) {
				loc = (Location) locationN.touserdata(Location.class);
			}
			if(args.narg()>=2 && args.arg(2).isnumber()) {
				number = args.arg(2).toint();
			}
			if(args.narg()>=3 && args.arg(3).isnumber()) {
				duration = args.arg(3).toint();
			}
			if(args.narg()>=4 && args.arg(4).isnumber()) {
				force = args.arg(4).tofloat();
			}
			if(args.narg()>=5 && args.arg(5).isstring()) {
				item = args.arg(5).toString();
			}
			
			if(loc!=null) {
				play(loc, number, duration, force, item);
				
				return LuaValue.TRUE;
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return LuaValue.FALSE;
	}
	
	private void play(Location location, int number, int duration, float force, String item) {
		// spawn items
		Random rand = new Random();
		Location loc = location.clone().add(0, 1, 0);
		final Item[] items = new Item[number];
		for (int i = 0; i < number; i++) {
			items[i] = loc.getWorld().dropItem(loc, ItemHandler.decodeItem(item, null));
			items[i].setVelocity(new Vector((rand.nextDouble()-.5) * force, (rand.nextDouble()-.5) * force, (rand.nextDouble()-.5) * force));
			items[i].setPickupDelay(duration * 2);
		}

		// schedule item deletion
		Bukkit.getScheduler().scheduleSyncDelayedTask(SpellItems.instance, new Runnable() {
			public void run() {
				for (int i = 0; i < items.length; i++) {
					items[i].remove();
				}
			}
		}, duration);
	}
}
