package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemReplace extends ItemSpell {
	/*private int range = 3;
	private int originalID = 1;
	private int originalDamage = 0;
	private int replaceID = 1;
	private int replaceDamage = 0;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) range = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==1) {
			String[] split = var.split(":");
			
			originalID = Integer.parseInt(split[0]);
			originalDamage = Integer.parseInt(split[1]);
		}
		else if(id==2) {
			String[] split = var.split(":");
			
			replaceID = Integer.parseInt(split[0]);
			replaceDamage = Integer.parseInt(split[1]);
		}
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) range = var.getAsInt();
		else if(id==1) {
			String[] split = var.getAsString().split(":");
			
			originalID = Integer.parseInt(split[0]);
			originalDamage = Integer.parseInt(split[1]);
		}
		else if(id==2) {
			String[] split = var.getAsString().split(":");
			
			replaceID = Integer.parseInt(split[0]);
			replaceDamage = Integer.parseInt(split[1]);
		}
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		return replaceNear(loc);
	}
	
	private boolean replaceNear(Location loc) {
		boolean replaced = false;
		
		for(int xx=-range; xx<=range; xx++) {
			for(int yy=-range; yy<=range; yy++) {
				for(int zz=-range; zz<=range; zz++) {
					Block block = loc.getWorld().getBlockAt(loc.getBlockX()+xx, loc.getBlockY()+yy, loc.getBlockZ()+zz);
					
					if(block.getTypeId()==originalID && block.getData()==originalDamage) {
						block.setTypeIdAndData(replaceID, (byte) replaceDamage, false);
					}
				}
			}
		}
		
		return replaced;
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=6) {
			LuaValue locN = args.arg(1);
			LuaValue rangeN = args.arg(2);
			LuaValue originalIDN = args.arg(3);
			LuaValue originalDamageN = args.arg(2);
			LuaValue replaceIDN = args.arg(3);
			LuaValue replaceDamageN = args.arg(2);
			
			if(locN.isuserdata(Location.class) && rangeN.isnumber() && originalIDN.isnumber() && originalDamageN.isnumber() && replaceIDN.isnumber() && replaceDamageN.isnumber()) {
				Location loc = (Location) locN.touserdata(Location.class);
				
				if(loc!=null) {
					int range = rangeN.toint();
					int originalID = originalIDN.toint();
					int originalDamage = originalDamageN.toint();
					int replaceID = replaceIDN.toint();
					int replaceDamage = replaceDamageN.toint();
					
					replaceNear(loc, range, originalID, originalDamage, replaceID, replaceDamage);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private boolean replaceNear(Location loc, int range, int originalID, int originalDamage, int replaceID, int replaceDamage) {
		boolean replaced = false;
		
		for(int xx=-range; xx<=range; xx++) {
			for(int yy=-range; yy<=range; yy++) {
				for(int zz=-range; zz<=range; zz++) {
					Block block = loc.getWorld().getBlockAt(loc.getBlockX()+xx, loc.getBlockY()+yy, loc.getBlockZ()+zz);
					
					if(block.getTypeId()==originalID && block.getData()==originalDamage) {
						block.setTypeIdAndData(replaceID, (byte) replaceDamage, false);
					}
				}
			}
		}
		
		return replaced;
	}
}
