import me.andre111.items.lua.GetLocation;
import me.andre111.items.lua.GetPlayer;
import me.andre111.items.lua.GetPlayerCount;
import me.andre111.items.lua.GetSpawn;
import me.andre111.items.lua.GetTime;
import me.andre111.items.lua.GetWorld;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

//Library for internal Lua scripts(require 'InternalLibrary')
public class InternalLibrary extends TwoArgFunction {
	@Override
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
			library.set("getLocation", new GetLocation());
			library.set("getWorld", new GetWorld());
			library.set("getTime", new GetTime());
			library.set("getSpawn", new GetSpawn());
			library.set("getPlayer", new GetPlayer());
			library.set("getPlayerCount", new GetPlayerCount());
		env.set("internalLib", library);
		return library;
	}
}
