package me.andre111.items.item.spell;

import java.util.ArrayList;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ItemLeap extends ItemSpell {
	private double forward = 40 / 10D;
	private double upward = 15 / 10D;
	private float power = 1;
	private boolean disableDamage = true;
	
	private double range;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) forward = var;
		else if(id==1) upward = var;
		else if(id==2) power = (float) var;
		else if(id==3) disableDamage = var==1;
		else if(id==4) range = var;
	}
	
	@Override
	public boolean cast(Player player) {
		spellLeap(player, forward, upward, power, disableDamage);
		return true;
	}
	
	@Override
	public boolean cast(Player player, Block target) {
		return cast(player);
	}
	@Override
	public boolean cast(Player player, Player target) {
		return cast(player);
	}
	
	@Override
	public boolean cast(Player player, Location loc) {
		ArrayList<Player> players = new ArrayList<Player>();
		for(Entity e : loc.getWorld().getEntities()) {
			if(e instanceof Player) {
				if(e.getLocation().distanceSquared(loc)<=range*range) {
					players.add((Player) e);
				}
			}
		}
		
		for(Player p : players) {
			spellLeap(p, forward, upward, power, disableDamage);
		}
		
		if(players.size()>0) {
			return true;
		}
		return false;
	}
	
	public static void spellLeap(Player player, double forward, double upward, float power, boolean diasableDamage) {
		Vector v = player.getLocation().getDirection();
        v.setY(0).normalize().multiply(forward*power).setY(upward*power);
        player.setVelocity(v);
        if(diasableDamage)
        	SpellItems.jumpingNormal.add(player);
	}
}
