package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class ItemBlink extends ItemSpell {
	private int range = 75;
	private boolean isReset = true;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) range = (int) Math.round(var);
		else if(id==1) isReset = (var==1);
	}
	
	@Override
	public boolean cast(Player player, Location location, Player target, Block block) {
		if(player==null) return false;
		
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
				player.teleport(loc);
				getItem().createEffects(loc.clone(), getAction(), "Teleport");
				//player.sendMessage(ConfigManager.getLanguage().getString("string_blink","You blink away!"));
				return true;
			} else {
				//player.sendMessage(ConfigManager.getLanguage().getString("string_cannot_blink","You cannot blink there!"));
				if(isReset) {
					resetCoolDown(player);
				}
				return false;
			}
		} else {
			//player.sendMessage(ConfigManager.getLanguage().getString("string_cannot_blink","You cannot blink there!"));
			if(isReset) {
				resetCoolDown(player);
			}
			return false;
		}
	}
	
	/*@Override
	public boolean cast(Player player, Location loc) {
		if (loc != null) {
			loc.setX(loc.getX()+.5);
			loc.setZ(loc.getZ()+.5);
			loc.setPitch(player.getLocation().getPitch());
			loc.setYaw(player.getLocation().getYaw());
			player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
			player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 0);
			player.teleport(loc);
			player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 0);
			//player.sendMessage(ConfigManager.getLanguage().getString("string_blink","You blink away!"));
			
			return true;
		}
		
		if(isReset) {
			resetCoolDown(player);
		}
		return false;
	}*/
}
