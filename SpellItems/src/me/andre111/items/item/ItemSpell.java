package me.andre111.items.item;

import java.util.HashMap;
import java.util.Map;

import me.andre111.items.SpellItems;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemSpell {
	
	public void setCastVar(int id, double var) {
	}
	public void setCastVar(int id, String var) {
	}
	public void setCastVar(int id, SpellVariable var) {
	}
	
	//player can be null!
	public boolean cast(Player player, Location loc, Player target, Block block, boolean[] states, HashMap<Integer, SpellVariable> variables) {
		//required other attacks to succed
		if(require!=-1) {
			if(!states[require]) return false;
		}
		
		//set vars from Variables
		currentVariables = variables;
		for(Map.Entry<Integer, Integer> vars : variable.entrySet()) {
			setCastVar(vars.getKey(), variables.get(vars.getValue()));
		}
		
		return cast(player, loc, target, block);
	}
	//player can be null!
	public boolean cast(Player player, Location loc, Player target, Block block) {
		return false;
	}
	
	private HashMap<Integer, SpellVariable> currentVariables;
	public HashMap<Integer, SpellVariable> getVariables() {
		return currentVariables;
	}
	
	//Type of Attack:
	//0: Simple Cast
	//1: Needs Target Block
	//2: Needs Target Player
	/*public int getType() {
		return 0;
	}*/
	
	
	private String itemName = "";
	private int action = 0;
	private int require = -1;
	
	private HashMap<Integer, Integer> variable = new HashMap<Integer, Integer>();
	
	public void setItemName(String name) {
		itemName = name;
	}
	public String getItemName() {
		return itemName;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public void setRequire(int r) {
		require = r;
	}
	public int getRequire() {
		return require;
	}
	public void setVariable(int id, int var) {
		variable.put(id, var);
	}
	
	public CustomItem getItem() {
		return SpellItems.itemManager.getItemByName(getItemName());
	}
	public void resetCoolDown(Player player) {
		getItem().resetCoolDown(getAction(), player);
	}
}
