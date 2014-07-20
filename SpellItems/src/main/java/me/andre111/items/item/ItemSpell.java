package me.andre111.items.item;

import java.util.HashMap;
import java.util.Map;

import me.andre111.items.SpellItems;

import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class ItemSpell extends VarArgFunction {
	//###############################################
	//Static Spellcontroll
	//###############################################
	public static Varargs RETURN_FALSE;
	public static Varargs RETURN_TRUE;
	static {
		LuaValue[] rfalse = new LuaValue[1];
		rfalse[0] = LuaValue.FALSE;
		RETURN_FALSE = LuaValue.varargsOf(rfalse);
		
		LuaValue[] rtrue = new LuaValue[1];
		rtrue[0] = LuaValue.TRUE;
		RETURN_TRUE = LuaValue.varargsOf(rtrue);
	}
	
	private static HashMap<String, ItemSpell> spells = new HashMap<String, ItemSpell>();
	
	public static void addSpellToLUA(ItemSpell spell, String name) {
		spells.put(name, spell);
	}
	
	public static ItemSpell getSpellFromLUAName(String name) {
		return spells.get(name);
	}
	
	public static void addSpellFunctions(LuaValue library) {
		for(Map.Entry<String, ItemSpell> entry : spells.entrySet()) {
			library.set(entry.getKey(), entry.getValue());
		}
	}
	//###############################################
	
	//player can be null!
	/*public boolean cast(Player player, Location loc, Player target, Block block, boolean[] states, HashMap<Integer, SpellVariable> variables) {
		//required other attacks to succed
		if(require!=-1) {
			if(!states[require]) return false;
		}
		
		//set vars from Variables
		currentVariables = variables;
		for(Map.Entry<Integer, Integer> vars : variable.entrySet()) {
			if(variables.get(vars.getValue())!=null) {
				setCastVar(vars.getKey(), variables.get(vars.getValue()));
			}
		}
		
		boolean success = cast(player, loc, target, block);
		currentVariables = null;
		
		return success;
	}
	//player can be null!
	public boolean cast(Player player, Location loc, Player target, Block block) {
		return false;
	}
	
	private HashMap<Integer, SpellVariable> currentVariables;
	public HashMap<Integer, SpellVariable> getVariables() {
		return currentVariables;
	}*/
	
	//Type of Attack:
	//0: Simple Cast
	//1: Needs Target Block
	//2: Needs Target Player
	/*public int getType() {
		return 0;
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		return null;
	}
	
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
