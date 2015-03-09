package me.andre111.items.item.spell;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemLay extends ItemSpell {
	/*private int radius;
	private String message = "";*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue playerN = args.arg(1);
			LuaValue radiusN = args.arg(2);
			LuaValue messageN = args.arg(3);
			
			if(playerN.isstring() && radiusN.isint() && messageN.isstring()) {
				Entity target = EntityHandler.getEntityFromUUID(playerN.toString());
				int radius = radiusN.toint();
				String message = messageN.toString();
				
				if(target!=null && target instanceof Player) {
					if(castAt((Player) target, target.getLocation(), radius, message))
						return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private boolean castAt(Player player, Location loc, int radius, String message) {
		if(ItemHandler.countItems(player, Material.MONSTER_EGG, 0)>=1) {
			ItemHandler.removeItems(player, Material.MONSTER_EGG, 0, 1);
			
			World w = loc.getWorld();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			for(int xx=-radius; xx<=radius; xx++) {
				for(int yy=-radius; yy<=radius; yy++) {
					for(int zz=-radius; zz<=radius; zz++) {
						Block block = w.getBlockAt(x+xx, y+yy, z+zz);
						Material bid = block.getType();
						if(bid==Material.STONE || bid==Material.COBBLESTONE || bid==Material.SMOOTH_BRICK) {
							block.setType(Material.MONSTER_EGGS);
						}
					}
				}
			}
			
			if(!message.equals(""))
				Bukkit.getServer().broadcastMessage(message);
			
			return true;
		} else {
			//player.sendMessage(ConfigManager.getLanguage().getString("string_need_egg","You need an Egg to Infect!"));
			player.sendMessage("You need an Egg to Infect!");
			
			return false;
		}
	}
}
