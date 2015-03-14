package me.andre111.items.lua;

import java.util.UUID;

import me.andre111.items.SpellItems;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class GetLooking extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			LuaValue distance = args.arg(2);
			LuaValue pathable = args.arg(3);
			
			Location loc = null;
			if(value.isuserdata(UUID.class) && distance.isnumber() && pathable.isboolean()) {
				Entity entity = EntityHandler.getEntityFromUUID((UUID) value.touserdata(UUID.class));
				if(entity!=null && entity instanceof Player) {
					Player player = (Player) entity;
					int range = distance.toint();
					
					loc = getLooking(player, range, pathable.toboolean());
				}
			}
			
			if(loc!=null) {
				LuaValue[] returnValue = new LuaValue[2];
				returnValue[0] = LuaValue.TRUE;
				returnValue[1] = LUAHelper.createLocationObject(loc);
				
				return LuaValue.varargsOf(returnValue);
			}
		}
		
		return LuaValue.FALSE;
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
