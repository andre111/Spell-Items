package me.andre111.items.item.spell;

import java.util.ArrayList;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.ItemVariableHelper;
import me.andre111.items.utils.PlayerHandler;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemPotionEffect extends ItemSpell {
	private boolean self = true;
	private ArrayList<String> effects = new ArrayList<String>();
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) self = (var==1);
	}
	
	@Override
	public void setCastVar(int id, String var) {
		if(id>0) effects.add(var);
	}
	
	@Override
	public void setCastVar(int id, Object var) {
		if(id==0) self = ItemVariableHelper.getVariableAndIntegerBoolean(var);
		else if(id>0) {
			effects.add(ItemVariableHelper.getVariableAsString(var));
		}
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
			addEffects(pTarget);
		}
		
		return false;
	}
	
	private void addEffects(Player player) {
		for(String st : effects) {
			String[] split = st.split(":");
			
			int id = Integer.parseInt(split[0]);
			int duration = Integer.parseInt(split[1]);
			int level = 0;
			if(split.length>2) level = Integer.parseInt(split[2]);
			
			if(!PlayerHandler.hasHigherPotionEffect(player, id, level)) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.getById(id), duration, level), true);
			}
		}
	}
}
