package me.andre111.items.item.spell;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.ItemVariableHelper;

public class ItemRandom extends ItemSpell {
	private Random rand = new Random();
	private int chance = 2;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) chance = (int)Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, Object var) {
		if(id==0) chance = ItemVariableHelper.getVariableAsInt(var);
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		return castIntern();
	}
	
	private boolean castIntern() {
		return rand.nextInt(chance)==0;
	}
}
