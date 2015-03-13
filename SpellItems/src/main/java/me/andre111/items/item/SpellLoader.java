package me.andre111.items.item;

import me.andre111.items.item.spell.ItemArmorCheck;
import me.andre111.items.item.spell.ItemArmorSet;
import me.andre111.items.item.spell.ItemBlink;
import me.andre111.items.item.spell.ItemCommand;
import me.andre111.items.item.spell.ItemDamage;
import me.andre111.items.item.spell.ItemDrop;
import me.andre111.items.item.spell.ItemEntityStack;
import me.andre111.items.item.spell.ItemExperience;
import me.andre111.items.item.spell.ItemExplode;
import me.andre111.items.item.spell.ItemGetItem;
import me.andre111.items.item.spell.ItemHunger;
import me.andre111.items.item.spell.ItemKill;
import me.andre111.items.item.spell.ItemLaunch;
import me.andre111.items.item.spell.ItemLay;
import me.andre111.items.item.spell.ItemLeap;
import me.andre111.items.item.spell.ItemPotionEffect;
import me.andre111.items.item.spell.ItemReplace;
import me.andre111.items.item.spell.ItemRewardPoints;
import me.andre111.items.item.spell.ItemRoar;
import me.andre111.items.item.spell.ItemSetDamage;
import me.andre111.items.item.spell.ItemSmash;
import me.andre111.items.item.spell.ItemSnowballs;
import me.andre111.items.item.spell.ItemTeleport;
import me.andre111.items.item.spell.ItemTransmute;
import me.andre111.items.item.spell.ItemVariableSet;
import me.andre111.items.item.spell.ItemVariableSetPlayerValue;
import me.andre111.items.item.spell.ItemWorldThunderStorm;
import me.andre111.items.item.spell.ItemWorldTornado;

public class SpellLoader {
	public static void addSpells() {
		ItemSpell.addSpellToLUA(new ItemArmorCheck(), "ItemArmorCheck");
		ItemSpell.addSpellToLUA(new ItemArmorSet(), "ItemArmorSet");
		ItemSpell.addSpellToLUA(new ItemBlink(), "ItemBlink");
		ItemSpell.addSpellToLUA(new ItemCommand(), "ItemCommand");
		ItemSpell.addSpellToLUA(new ItemDamage(), "ItemDamage");
		ItemSpell.addSpellToLUA(new ItemDrop(), "ItemDrop");
		ItemSpell.addSpellToLUA(new ItemEntityStack(), "ItemEntityStack");
		ItemSpell.addSpellToLUA(new ItemExperience(), "ItemExperience");
		ItemSpell.addSpellToLUA(new ItemExplode(), "ItemExplode");
		ItemSpell.addSpellToLUA(new ItemGetItem(), "ItemGetItem");
		ItemSpell.addSpellToLUA(new ItemHunger(), "ItemHunger");
		ItemSpell.addSpellToLUA(new ItemKill(), "ItemKill");
		ItemSpell.addSpellToLUA(new ItemLaunch(), "ItemLaunch");
		ItemSpell.addSpellToLUA(new ItemLay(), "ItemLay");
		ItemSpell.addSpellToLUA(new ItemLeap(), "ItemLeap");
		ItemSpell.addSpellToLUA(new ItemPotionEffect(), "ItemPotionEffect");
		ItemSpell.addSpellToLUA(new ItemReplace(), "ItemReplace");
		ItemSpell.addSpellToLUA(new ItemRewardPoints(), "ItemRewardPoints");
		ItemSpell.addSpellToLUA(new ItemRoar(), "ItemRoar");
		ItemSpell.addSpellToLUA(new ItemSetDamage(), "ItemSetDamage");
		ItemSpell.addSpellToLUA(new ItemSmash(), "ItemSmash");
		ItemSpell.addSpellToLUA(new ItemSnowballs(), "ItemSnowballs");
		ItemSpell.addSpellToLUA(new ItemTeleport(), "ItemTeleport");
		ItemSpell.addSpellToLUA(new ItemTransmute(), "ItemTransmute");
		ItemSpell.addSpellToLUA(new ItemVariableSet(), "ItemVariableSet");
		ItemSpell.addSpellToLUA(new ItemVariableSetPlayerValue(), "ItemVariableSetPlayerValue");
		ItemSpell.addSpellToLUA(new ItemWorldThunderStorm(), "ItemWorldThunderStorm");
		ItemSpell.addSpellToLUA(new ItemWorldTornado(), "ItemWorldTornado");
	}
}
