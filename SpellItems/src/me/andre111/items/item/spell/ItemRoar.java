package me.andre111.items.item.spell;

import java.util.List;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.ItemVariableHelper;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;

public class ItemRoar extends ItemSpell {
	private double range;
	private String message = "";
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) range = var;
	}
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==1) message = var;
	}
	
	@Override
	public void setCastVar(int id, Object var) {
		if(id==0) range = ItemVariableHelper.getVariableAsDouble(var);
		else if(id==1) message = ItemVariableHelper.getVariableAsString(var);
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		if(player!=null && player.getLocation()==loc) {
			return castAtEntity(player, player);
		} else {
			Arrow a = (Arrow) player.getWorld().spawnEntity(loc, EntityType.ARROW);
			boolean success = castAtEntity(a, player);
			a.remove();
			
			return success;
		}
	}
	
	private boolean castAtEntity(Entity ent, Player damage) {
		boolean success = false;
		List<Entity> entities = ent.getNearbyEntities(range, range, range);
        for (Entity e : entities) {
        	if (e instanceof Silverfish) {
        		((Silverfish)e).damage((double) 0, damage);
        		success = true;
        	}
        }
		
        if(!message.equals(""))
        	//Bukkit.getServer().broadcastMessage(ConfigManager.getLanguage().getString("string_brood_roar","A Broodmother roars!"));
        	Bukkit.getServer().broadcastMessage("A Broodmother roars!");
        
        return success;
	}
}
