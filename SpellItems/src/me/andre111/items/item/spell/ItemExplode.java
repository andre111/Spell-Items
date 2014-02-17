package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemExplode extends ItemSpell {
	/*private float power = 6F;
	private boolean kill = true;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) power = (float) var;
		else if(id==1) kill = (var==1);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) power = (float) var.getAsDouble();
		else if(id==1) kill = var.getAsIntBoolean();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		World w = player.getWorld();
		//Location loc = player.getLocation();
		
		if(kill && player!=null) {
			player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			player.damage((double) 10000);
		}
		
		w.createExplosion(loc, power);
		w.createExplosion(loc, power);
		
		return true;
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue powerN = args.arg(1);
			LuaValue locN = args.arg(2);
			
			if(powerN.isnumber() && locN.isuserdata(Location.class)) {
				float power = (float)powerN.todouble();
				Location loc = (Location) locN.touserdata(Location.class);
				
				if(loc!=null) {
					loc.getWorld().createExplosion(loc, power);
					loc.getWorld().createExplosion(loc, power);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
