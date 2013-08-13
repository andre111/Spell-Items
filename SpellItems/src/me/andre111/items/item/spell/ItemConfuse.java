package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemConfuse extends ItemSpell {
	private int duration = 300;
	private int level = 0;

	@Override
	public void setCastVar(int id, double var) {
		if(id==0) duration = (int) Math.round(var);
		else if(id==1) level = (int) Math.round(var);
	}
	
	@Override
	public boolean cast(Player player, Player target) {
		target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration, level), true);
		target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, level), true);
		
		return true;
	}
	
	@Override
	public boolean cast(Player player) {
		resetCoolDown(player);
		return false;
	}
	@Override
	public boolean cast(Player player, Block block) {
		resetCoolDown(player);
		return false;
	}
	@Override
	public boolean cast(Player player, Location loc) {
		resetCoolDown(player);
		return false;
	}
}