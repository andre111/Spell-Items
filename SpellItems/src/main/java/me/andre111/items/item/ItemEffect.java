package me.andre111.items.item;

import org.bukkit.Location;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class ItemEffect extends VarArgFunction {
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

	private String location;

	//the location when and where to play the effect
	public void setLocation(String loc) {
		location = loc;
	}
	public String getLocation() {
		return location;
	}

	//set arguments and other stuff
	public void setVars(String vars) {
	}

	//play the effect at the given location
	public void play(Location loc) {
	}

	@Override
	public Varargs invoke(Varargs args) {
		return null;
	}
}
