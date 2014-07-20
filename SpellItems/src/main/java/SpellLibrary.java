import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.effect.ItemEffectItemSpray;
import me.andre111.items.item.effect.ItemEffectLightning;
import me.andre111.items.item.effect.ItemEffectNormal;
import me.andre111.items.item.effect.ItemEffectSound;
import me.andre111.items.item.utils.LUACreateEffect;
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
			utils.set("CreateEffect", new LUACreateEffect());
			utils.set("DistanceSquared", new LUADistanceSquared());
			utils.set("SendMessage", new LUASendMessage());
		env.set("utils", utils);
		LuaValue effects = tableOf();
			effects.set("CreateItemSpray", new ItemEffectItemSpray()); //loc, number, duration, force, item
			effects.set("CreateLightning", new ItemEffectLightning()); //loc
			effects.set("CreateParticle", new ItemEffectNormal()); //loc, effectname, data
			effects.set("CreateSound", new ItemEffectSound()); //loc, soundname, volume, pitch
		env.set("effects", effects);
		return library;
	}
}
