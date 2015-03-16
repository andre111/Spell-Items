import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.effect.LuaEffectItemSpray;
import me.andre111.items.item.effect.LuaItemEffectLightning;
import me.andre111.items.item.effect.LuaItemEffectNormal;
import me.andre111.items.item.effect.LuaItemEffectSound;
import me.andre111.items.item.utils.LUADistanceSquared;
import me.andre111.items.item.utils.LUASendMessage;

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
			utils.set("DistanceSquared", new LUADistanceSquared());
			utils.set("SendMessage", new LUASendMessage());
		env.set("utils", utils);
		LuaValue effects = tableOf();
			effects.set("CreateItemSpray", new LuaEffectItemSpray()); //loc, number, duration, force, item
			effects.set("CreateLightning", new LuaItemEffectLightning()); //loc
			effects.set("CreateParticle", new LuaItemEffectNormal()); //loc, effectname, data
			effects.set("CreateSound", new LuaItemEffectSound()); //loc, soundname, volume, pitch
		env.set("effects", effects);
		return library;
	}
}
