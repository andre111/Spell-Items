package me.andre111.items.convert;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class SpellConvertFunction {
	private String name;
	private ArrayList<SpellConvertSpell> spells = new ArrayList<SpellConvertSpell>();
	
	public SpellConvertFunction(String n) {
		name = n;
	}
	
	//read old configfile to create lua scripts
	public void load(FileConfiguration config, String key) {
		ConfigurationSection as = config.getConfigurationSection(key);
		if(as!=null) {
			Set<String> strings2 = as.getKeys(false);
			int spellid = 0;
			if(strings2.size()>0) {
				String[] casts = strings2.toArray(new String[strings2.size()]);

				for(int i=0; i<casts.length; i++) {
					loadCast(config, key+"."+casts[i]+".", spellid);
					spellid ++;
				}
			}
		}
	}
	
	//read old configfile to create lua scripts
	private void loadCast(FileConfiguration config, String basename, int id) {
		String cast = convertToLuaFunction(config.getString(basename+"cast", ""));
		int require = config.getInt(basename+"require", -1);
		List<String> arguments = config.getStringList(basename+"castVars");
		
		SpellConvertSpell spell = new SpellConvertSpell(id, cast);
		SpellConvertArguments.addArguments(spell, cast, arguments);
		
		if(require==-1) {
			spells.add(spell);
		} else {
			for(SpellConvertSpell rspell : spells) {
				rspell.addSpell(spell, require);
			}
		}
	}
	
	private String convertToLuaFunction(String function) {
		return "spell."+function;
	}
	
	//write new luascript
	public void save(BufferedWriter writer) throws IOException {
		writer.write("function "+name+"(player, target, block, location)");
		writer.newLine();
			for(SpellConvertSpell spell : spells) {
				spell.save(writer, "   ");
			}
		writer.write("end");
		writer.newLine();
	}
}
