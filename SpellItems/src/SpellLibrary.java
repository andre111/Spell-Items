import me.andre111.items.item.ItemSpell;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

//Library for Lua scripts(require 'SpellLibrary')
public class SpellLibrary extends TwoArgFunction {
	@Override
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
			ItemSpell.addSpellFunctions(library);
		env.set("spell", library);
		return library;
	}
}
