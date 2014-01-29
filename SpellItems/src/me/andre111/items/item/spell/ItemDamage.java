package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemDamage extends ItemSpell {
	private boolean self = true;
	private int damage = 4;
	
	@SuppressWarnings("unused")
	private double range;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) self = var==1;
		else if(id==1) damage = (int) Math.round(var);
		else if(id==2) range = var;
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) self = var.getAsIntBoolean();
		else if(id==1) damage = var.getAsInt();
		else if(id==2) range = var.getAsDouble();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Player pTarget = null;
		if(self) {
			pTarget = player;
		} else if(target!=null) {
			pTarget = target;
		}
		
		if(pTarget!=null) {
			castIntern(pTarget, player);
		}
		
		return false;
	}
	
	/*@Override
	public boolean cast(Player player, Location loc) {
		if(self) return false;
		
		ArrayList<Player> players = new ArrayList<Player>();
		for(Entity e : loc.getWorld().getEntities()) {
			if(e instanceof Player) {
				if(e.getLocation().distanceSquared(loc)<=range*range) {
					players.add((Player) e);
				}
			}
		}
		
		for(Player p : players) {
			castIntern(p, player);
		}
		
		if(players.size()>0) {
			return true;
		}
		return false;
	}*/
	
	private boolean castIntern(Player player, Player source) {
		if(damage>0) {
			player.damage((double) damage, source);
		} else {
			double newHealth = player.getHealth() - damage;
			if(newHealth>player.getMaxHealth()) newHealth = player.getMaxHealth();
			
			player.setHealth((double) newHealth);
		}
		
		return true;
	}
}