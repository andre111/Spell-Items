package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class ItemExplode extends ItemSpell {
	private float power = 6F;
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
	}
}
