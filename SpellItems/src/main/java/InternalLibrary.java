import me.andre111.items.lua.GetFoodLevel;
import me.andre111.items.lua.GetGameMode;
import me.andre111.items.lua.GetHealth;
import me.andre111.items.lua.GetLocation;
import me.andre111.items.lua.GetLooking;
import me.andre111.items.lua.GetPlayer;
import me.andre111.items.lua.GetPlayerCount;
import me.andre111.items.lua.GetSaturation;
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
			library.set("getLooking", new GetLooking());
			library.set("getHealth", new GetHealth());
			library.set("getFoodLevel", new GetFoodLevel());
			library.set("getSaturation", new GetSaturation());
			library.set("getGameMode", new GetGameMode());
		env.set("internalLib", library);
		return library;
	}
}
