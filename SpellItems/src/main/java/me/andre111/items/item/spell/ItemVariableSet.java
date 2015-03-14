package me.andre111.items.item.spell;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemVariableSet extends ItemSpell {
	private Random rand = new Random();
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue variableN = args.arg(1);
			LuaValue objectN = LUAHelper.getInternalValue(args.arg(2));
			
			if(variableN.isstring()) {
				String value = variableN.toString();
				
				Entity player = null;
				if(objectN.isuserdata(UUID.class)) {
					player = EntityHandler.getEntityFromUUID((UUID) objectN.touserdata(UUID.class));
				}
				Block block = null;
				if(objectN.isuserdata(Block.class)) {
					block = (Block) objectN.touserdata(Block.class);
				}
				Location location = null;
				if(objectN.isuserdata(Location.class)) {
					location = (Location) objectN.touserdata(Location.class);
				}
				
				LuaValue[] returnValue = new LuaValue[2];
				returnValue[0] = LuaValue.TRUE;
				
				//Locations
				if(value.equalsIgnoreCase("playerPos") || value.equalsIgnoreCase("entityPos")) {
					if(player!=null) {
						returnValue[1] = LUAHelper.createLocationObject(player.getLocation());
						return LuaValue.varargsOf(returnValue);
					}
				} else if(value.equalsIgnoreCase("blockPos")) {
					if(block!=null) {
						returnValue[1] = LUAHelper.createLocationObject(block.getLocation());
						return LuaValue.varargsOf(returnValue);
					}
				} else if(value.equalsIgnoreCase("worldSpawn")) {
					Location loc = location;
					if(player!=null) {
						loc = player.getLocation();
					}
					if(block!=null) {
						loc = block.getLocation();
					}
					if(loc!=null) {
						returnValue[1] = LUAHelper.createLocationObject(loc.getWorld().getSpawnLocation());
						return LuaValue.varargsOf(returnValue);
					}
				//Players
				} else if(value.equalsIgnoreCase("randomPlayer")) {
					Location loc = location;
					if(player!=null) {
						loc = player.getLocation();
					}
					if(block!=null) {
						loc = block.getLocation();
					}
					if(loc!=null) {
						List<Player> players = loc.getWorld().getPlayers();
						int pos = rand.nextInt(players.size());
						returnValue[1] = LUAHelper.createEntityObject(players.get(pos));
						return LuaValue.varargsOf(returnValue);
					}
				//Numbers
				} else if(value.equalsIgnoreCase("time")) {
					Location loc = location;
					if(player!=null) {
						loc = player.getLocation();
					}
					if(block!=null) {
						loc = block.getLocation();
					}
					if(loc!=null) {
						returnValue[1] = LuaValue.valueOf(0.0D+loc.getWorld().getTime());
						return LuaValue.varargsOf(returnValue);
					}
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
