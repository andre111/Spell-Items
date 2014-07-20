package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.world.WorldTornado;

import org.bukkit.Location;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemWorldTornado extends ItemSpell {
	/*private int time = 10*20;
	private double moveSpeed = 0.05;
	private int changeChance = 1;
	
	private int blockChance = 70;
	private int radius = 3;
	private boolean hurt = false;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=7) {
			LuaValue locN = args.arg(1);
			LuaValue timeN = args.arg(2);
			LuaValue moveSpeedN = args.arg(3);
			LuaValue changeChanceN = args.arg(4);
			LuaValue blockChanceN = args.arg(5);
			LuaValue radiusN = args.arg(6);
			LuaValue hurtN = args.arg(7);
			
			if(locN.isuserdata(Location.class) && timeN.isstring() && moveSpeedN.isnumber() && changeChanceN.isstring() && blockChanceN.isnumber() && radiusN.isstring() && hurtN.isboolean()) {
				Location loc = (Location) locN.touserdata(Location.class);
				int time = timeN.toint();
				double moveSpeed = moveSpeedN.todouble();
				int changeChance = changeChanceN.toint();
				int blockChance = blockChanceN.toint();
				int radius = radiusN.toint();
				boolean hurt = hurtN.toboolean();
				
				if(loc!=null) {
					castIntern(loc, time, moveSpeed, changeChance, blockChance, radius, hurt);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private boolean castIntern(Location loc, int time, double moveSpeed, int changeChance, int blockChance, int radius, boolean hurt) {
		WorldTornado effect = new WorldTornado(moveSpeed, changeChance, blockChance, radius, hurt);
		effect.start(loc.getWorld(), loc, time);
		
		return true;
	}
}
