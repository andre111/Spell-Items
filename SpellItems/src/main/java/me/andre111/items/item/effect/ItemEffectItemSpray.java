package me.andre111.items.item.effect;

import java.util.Random;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemEffect;
import me.andre111.items.utils.PlayerHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemEffectItemSpray extends ItemEffect {
	private int number = 1;
	private int duration = 6;
	private float force = 1.0F; 
	private String item;
	
	@Override
	public void setVars(String vars) {
		String[] split = vars.split(";");
		
		if(split.length>0) number = Integer.parseInt(split[0]);
		if(split.length>1) duration = Integer.parseInt(split[1]);
		if(split.length>2) force = Float.parseFloat(split[2]);
		if(split.length>3) item = split[3];
	}

	@Override
	public void play(Location location) {
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
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue locationN = args.arg(1);
			
			//Get Location
			Location loc = null;
			if(locationN.isuserdata(Location.class)) {
				loc = (Location) locationN.touserdata(Location.class);
			} else if(locationN.isuserdata(Block.class)) {
				loc = ((Block) locationN.touserdata(Block.class)).getLocation();
			} else if(locationN.isstring()) {
				Player player = PlayerHandler.getPlayerFromUUID(locationN.toString());
				if(player!=null) {
					loc = player.getLocation();
				}
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
			
			play(loc);
			
			return RETURN_TRUE;
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
