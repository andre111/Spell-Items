package me.andre111.items;

import java.io.File;

import me.andre111.items.lua.LUAHelper;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaController {
	public Globals globals;
	
	public LuaController() {
		// create an environment to run in
		globals = JsePlatform.standardGlobals();
		
		String script = new File(SpellItems.instance.getDataFolder(), "config" +  File.separatorChar + "internal" +  File.separatorChar + "objects.lua").getAbsolutePath();
		try {
			LuaValue chunk = globals.loadfile(script);
			
			chunk.call( LuaValue.valueOf(script) );
		} catch (LuaError error) {
			System.out.println(error.getLocalizedMessage());
		}
	}
	
	public void loadScript(String script) {
		try {
			// Use the convenience function on the globals to load a chunk.
			LuaValue chunk = globals.loadfile(script);
			
			// Use any of the "call()" or "invoke()" functions directly on the chunk.
			chunk.call( LuaValue.valueOf(script) );
		} catch (LuaError error) {
			System.out.println(error.getLocalizedMessage());
		}
	}
	
	public boolean castFunction(String name, Entity player, Entity target, Block block, Location loc, int enchantLevel, double damage) {
		try {
			if(globals.get(name).isfunction()) {
				LuaValue[] args = new LuaValue[6];
				args[0] = LUAHelper.createEntityObject(player);
				args[1] = LUAHelper.createEntityObject(target);
				args[2] = LUAHelper.createBlockObject(block);
				args[3] = LUAHelper.createLocationObject(loc);
				args[4] = LuaValue.valueOf(enchantLevel);
				args[5] = LuaValue.valueOf(damage);
				
				Varargs vars = globals.get(name).invoke(LuaValue.varargsOf(args));
				
				if(vars.narg()>0) {
					LuaValue returnVal = vars.arg(1);
							
					if(returnVal.isboolean())
						return returnVal.toboolean();
				}
			}
		} catch (LuaError error) {
			System.out.println(error.getLocalizedMessage());
		}
		
		return false;
	}
	
	public void tick() {
		try {
			if(globals.get("tick").isfunction()) {
				globals.get("tick").invoke();
			}
		} catch (LuaError error) {
			System.out.println(error.getLocalizedMessage());
		}
	}
}
