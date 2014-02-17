package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;
import me.andre111.items.volatileCode.DynamicClassFunctions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemLaunch extends ItemSpell {
	private int blockId = 1;
	private byte blockData = 0;

	private double power = 1;
	
	private boolean drop = false;
	private boolean block = false;
	
	private boolean damage = false;
	private int hurt = 4;
	
	private ItemSpell onHit;
	
	@Override
	public void setCastVar(int id, String var) {
		//onHit
		if(id==7) {
			try {
				if(!var.contains("me.andre111.dvz.item.spell.")) {
					var = "me.andre111.dvz.item.spell." + var;
				}
				Class<?> c = Class.forName(var);
				if(c.getSuperclass().equals(ItemSpell.class)) {
					onHit = (ItemSpell) c.newInstance();
					onHit.setItemName(getItemName());
					onHit.setAction(getAction());
				}
			} catch (ClassNotFoundException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		} 
		//castVars für onHit
		else if(id>7) {
			if(onHit!=null) onHit.setCastVar(id-6, var);
		}
	}
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) blockId = (int) Math.round(var);
		else if(id==1) blockData = (byte) Math.round(var);
		else if(id==2) power = var;
		else if(id==3) drop = var==1;
		else if(id==4) block = var==1;
		else if(id==5) damage = var==1;
		else if(id==6) hurt = (int) Math.round(var);
		//castVars für onHit
		else if(id>7) {
			if(onHit!=null) onHit.setCastVar(id-6, var);
		}
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) blockId = var.getAsInt();
		else if(id==1) blockData = (byte) var.getAsInt();
		else if(id==2) power = var.getAsDouble();
		else if(id==3) drop = var.getAsIntBoolean();
		else if(id==4) block = var.getAsIntBoolean();
		else if(id==5) damage = var.getAsIntBoolean();
		else if(id==6) hurt = var.getAsInt();
		else if(id==7) {
			try {
				String varS = var.getAsString();
				if(!varS.contains("me.andre111.dvz.item.spell.")) {
					varS = "me.andre111.dvz.item.spell." + varS;
				}
				Class<?> c = Class.forName(varS);
				if(c.getSuperclass().equals(ItemSpell.class)) {
					onHit = (ItemSpell) c.newInstance();
					onHit.setItemName(getItemName());
					onHit.setAction(getAction());
				}
			} catch (ClassNotFoundException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		} 
		else if(id>7) {
			if(onHit!=null) {
				try {
					onHit.setCastVar(id-6, Double.parseDouble(var.getAsString()));
				} catch(NumberFormatException e) {
					onHit.setCastVar(id-6, var.getAsString());
				}
			}
		}
	}
	
	@Override
	public boolean cast(Player player, Location location, Player target, Block blockClicked) {
		if(player==null) return false;
		
		Location loc = player.getEyeLocation();
		FallingBlock fs = loc.getWorld().spawnFallingBlock(loc, blockId, blockData);
		
		Vector velocity = loc.getDirection().normalize().multiply(power);
		fs.setVelocity(velocity);
		
		fs.setDropItem(drop);
		if(!block) fs.setMetadata("spellitems_falling_noblock", new FixedMetadataValue(SpellItems.instance, 0));
		
		//make it do damage
		if(damage) {
			DynamicClassFunctions.setFallingBlockHurtEntities(fs, hurt, hurt);
		}
		
		fs.setMetadata("spellitems_falling_casting", new FixedMetadataValue(SpellItems.instance, this));
		fs.setMetadata("spellitems_falling_playername", new FixedMetadataValue(SpellItems.instance, player.getName()));
	
		return true;
	}

	public void onHit(Player player, Block block) {
		//effects
		getItem().createEffects(block.getLocation(), getAction(), "onHit");
		
		if(onHit!=null && player!=null)
			onHit.cast(player, block.getLocation(), null, null);
	}
	
	
	@Override
	public Varargs invoke(Varargs args) {
		//TODO - reimplement with lua system
		
		return RETURN_FALSE;
	}
}
