package me.andre111.items.convert;

import java.util.List;

import me.andre111.items.SpellItems;

public class SpellConvertArguments {
	
	public static void addArguments(SpellConvertSpell spell, String cast, List<String> arguments) {
		for(SpellDefault sdefault : SpellDefault.values()) {
			if(sdefault.getName().equals(cast)) {
				//TODO - read real arguments
				for(String argument : sdefault.getDefaultArguments()) {
					spell.addArgument(argument);
				}
				SpellItems.log("[Converter] WARNING: Using default arguments for: "+cast);
				return;
			}
		}
		
		SpellItems.log("[Converter] WARNING: Unknown Spell: "+cast);
	}
	
	private static enum SpellDefault {
		ITEMARMORCHECK("spell.ItemArmorCheck", "target", "false"),
		ITEMARMORSET("spell.ItemArmorSet", "target", "", "", "", ""),
		ITEMBLINK("spell.ItemBlink", "player", "75"),
		ITEMCOMMAND("spell.ItemCommand", "player", "false", ""),
		ITEMCONFUSE("spell.ItemConfuse", "target", "300", "0"),
		ITEMDAMAGE("spell.ItemDamage", "player", "target", "4"),
		ITEMDROP("spell.ItemDrop", "target"),
		ITEMEXPERIENCE("spell.ItemExperience", "player", "3"),
		ITEMEXPLODE("spell.ItemExplode", "6", ""),
		ITEMGETITEM("spell.ItemGetItem", "player", "", "20"),
		ITEMHUNGER("spell.ItemHunger", "target", "2"),
		ITEMKILL("spell.ItemKill", "player"),
		ITEMLAUNCH("spell.ItemLaunch"),
		ITEMLAY("spell.ItemLay", "player", "1", ""),
		ITEMLEAP("spell.ItemLeap", "player", "4", "1.5", "1", "true"),
		ITEMPOTIONEFFECT("spell.ItemPotionEffect", "player", ""),
		ITEMREPLACE("spell.ItemReplace", " ", "3", "1", "0", "1", "0"),
		ITEMREWARDPOINTS("spell.ItemRewardPoints", "player", "1"),
		ITEMROAR("spell.ItemRoar", "player", "15", ""),
		ITEMSETDAMAGE("spell.ItemSetDamage", "player", "0"),
		ITEMSMASH("spell.ItemSmash", " ", "loc"),
		ITEMSNOWBALLS("spell.ItemSnowballs", "player", "96", "You need 96 Snowballs!"),
		ITEMTELEPORT("spell.ItemTeleport", "player", ""),
		ITEMTRANSMUTE("spell.ItemTransmute", "player", "0", "0", "0", "", "false"),
		ITEMWORDTHUNDERSTORM("spell.ItemWorldThunderStorm", "", "200", "8", "10"),
		ITEMWORDTORNADO("spell.ItemTornado", "", "200", "0.05", "1", "70", "3", "false");
		
		private String name;
		private String[] dargs;
		
		SpellDefault(String n, String ...defaultargs) {
			name = n;
			dargs = defaultargs;
		}
		
		public String getName() {
			return name;
		}
		public String[] getDefaultArguments() {
			return dargs;
		}
	}
}
