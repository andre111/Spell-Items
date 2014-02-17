package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemConfuse extends ItemSpell {
	/*private int duration = 300;
	private int level = 0;

	@Override
	public void setCastVar(int id, double var) {
		if(id==0) duration = (int) Math.round(var);
		else if(id==1) level = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) duration = var.getAsInt();
		else if(id==1) level = var.getAsInt();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		if(target==null) {
			if(player!=null) resetCoolDown(player);
			return false;
		}
		
		target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration, level), true);
		target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, level), true);
		
		return true;
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue playerN = args.arg(1);
			LuaValue durationN = args.arg(2);
			LuaValue levelN = args.arg(3);
			
			if(playerN.isstring() && durationN.isnumber() && levelN.isnumber()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				int duration = durationN.toint();
				int level = levelN.toint();
				
				if(player!=null) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration, level), true);
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, level), true);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
