package me.andre111.items.item.spell;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.ItemVariableHelper;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemSmash extends ItemSpell {
	private boolean playSound = true;
	private boolean isReset = true;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) playSound = (var==1);
		else if(id==1) isReset = (var==1);
	}
	
	@Override
	public void setCastVar(int id, Object var) {
		if(id==0) playSound = ItemVariableHelper.getVariableAndIntegerBoolean(var);
		else if(id==1) isReset = ItemVariableHelper.getVariableAndIntegerBoolean(var);
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Location targetLoc = null;
		
		if(block!=null) {
			targetLoc = block.getLocation();
		} else if(loc!=null) {
			targetLoc = loc;
		}
		
		if(targetLoc!=null) {
			World w = targetLoc.getWorld();

			w.createExplosion(targetLoc, 2);
			//that will break anything-removed
			//w.getBlockAt(target).setTypeId(0);
			if(playSound)
				w.playSound(targetLoc, Sound.IRONGOLEM_THROW, 1, 1);
			
			return true;
		}
		
		if(player!=null && isReset) {
			resetCoolDown(player);
		}
		return false;
	}
}
