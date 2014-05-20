import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.utils.CreateEffect;
import me.andre111.items.item.utils.CreateNewEffect;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

//Library for Lua scripts(require 'SpellLibrary')
public class SpellLibrary extends TwoArgFunction {
	@Override
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
			ItemSpell.addSpellFunctions(library);
		env.set("spell", library);
		LuaValue utils = tableOf();
			utils.set("CreateEffect", new CreateEffect());
			utils.set("CreateNewEffect", new CreateNewEffect());
		env.set("utils", utils);
		return library;
	}
}
