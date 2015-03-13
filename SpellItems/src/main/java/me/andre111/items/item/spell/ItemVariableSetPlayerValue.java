package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.EntityHandler;
import me.andre111.items.volatileCode.DeprecatedMethods;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemVariableSetPlayerValue extends ItemSpell {
	/*private int variable = 0;
	private String playername = "";
	private String value = "";*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue valueN = args.arg(2);
			
			if(playerN.isstring() && valueN.isstring()) {
				Entity target = EntityHandler.getEntityFromUUID(playerN.toString());
				String value = valueN.toString();
				
				LuaValue[] returnValue = new LuaValue[4];
				returnValue[0] = LuaValue.TRUE;
				
				if(target!=null && target instanceof Player) {
					Player player = (Player) target;
					//Locations
					if(value.equalsIgnoreCase("location")) {
						returnValue[1] = LuaValue.userdataOf(player.getLocation());
					} else if(value.equalsIgnoreCase("spawn")) {
						Location sloc = player.getBedSpawnLocation();
						if(sloc==null) sloc = player.getWorld().getSpawnLocation();
						returnValue[1] = LuaValue.userdataOf(sloc);
					} else if(value.equalsIgnoreCase("looking")) {
						int distance = 50;
						if(args.narg()>=3) {
							if(args.arg(3).isnumber()) {
								distance = args.arg(3).toint();
							}
						}
						
						Location loc = getLooking(player, distance, false);
						Location locPath = getLooking(player, distance, true);
						if(loc==null) {
							returnValue[0] = LuaValue.FALSE;
						} else {
							returnValue[0] = LuaValue.TRUE;
							returnValue[1] = LuaValue.userdataOf(loc);
						}
						
						
						//additional pathable location
						if(locPath==null) {
							returnValue[2] = LuaValue.FALSE;
						} else {
							returnValue[2] = LuaValue.TRUE;
							returnValue[3] = LuaValue.userdataOf(locPath);
						}
					//Numbers
					} else if(value.equalsIgnoreCase("health")) {
						returnValue[1] = LuaValue.valueOf(player.getHealth());
					} else if(value.equalsIgnoreCase("food")) {
						returnValue[1] = LuaValue.valueOf(0.0D+player.getFoodLevel());
					} else if(value.equalsIgnoreCase("saturation")) {
						returnValue[1] = LuaValue.valueOf(0.0D+player.getSaturation());
					} else if(value.equalsIgnoreCase("gamemode")) {
						returnValue[1] = LuaValue.valueOf(0.0D+DeprecatedMethods.getGameModeValue(player.getGameMode()));
					}
					return LuaValue.varargsOf(returnValue);
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private Location getLooking(Player player, int range, boolean pathable) {
		BlockIterator iter; 
		try {
			iter = new BlockIterator(player, range>0&&range<150?range:150);
		} catch (IllegalStateException e) {
			iter = null;
		}
		Block prev = null;
		Block found = null;
		Block b;
		if (iter != null) {
			while (iter.hasNext()) {
				b = iter.next();
				if (SpellItems.isPathable(b.getType())) {
					prev = b;
				} else {
					found = b;
					break;
				}
			}
		}

		if (found != null) {
			if(pathable) {
				Location loc = null;
				if (range > 0 && !(found.getLocation().distanceSquared(player.getLocation()) < range*range)) {
				} else if (SpellItems.isPathable(found.getRelative(0,1,0)) && SpellItems.isPathable(found.getRelative(0,2,0))) {
					// try to stand on top
					loc = found.getLocation();
					loc.setY(loc.getY() + 1);
				} else if (prev != null && SpellItems.isPathable(prev) && SpellItems.isPathable(prev.getRelative(0,1,0))) {
					// no space on top, put adjacent instead
					loc = prev.getLocation();
				}
				if (loc != null) {
					loc.setX(loc.getX()+.5);
					loc.setZ(loc.getZ()+.5);
					loc.setPitch(player.getLocation().getPitch());
					loc.setYaw(player.getLocation().getYaw());
					return loc;
				} else {
					return null;
				}
			} else {
				return found.getLocation();
			}
		} else {
			return null;
		}
	}
}
