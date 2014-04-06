package me.andre111.items.convert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import me.andre111.items.SpellItems;
import me.andre111.items.config.SIFileConfiguration;

public class SpellConverter_1_3_to_2_0 {

	public static void convert(String oldconfig) {
		//load old file
		FileConfiguration configfile = SIFileConfiguration.loadConfiguration(new File(SpellItems.instance.getDataFolder(), oldconfig));
		FileConfiguration newconfigfile = SIFileConfiguration.loadConfiguration(new File(SpellItems.instance.getDataFolder(), oldconfig));
	
		//strip the new config file
		ConfigurationSection section = newconfigfile.getConfigurationSection("items");
		Set<String> keys = section.getKeys(false);
		for(String item : keys) {
			if (newconfigfile.contains("items."+item+".rightclick.casts")) {
				newconfigfile.set("items."+item+".rightclick.casts", null);
				newconfigfile.set("items."+item+".rightclick.lua", item+"_RC");
			}
			if (newconfigfile.contains("items."+item+".leftclick.casts")) {
				newconfigfile.set("items."+item+".leftclick.casts", null);
				newconfigfile.set("items."+item+".leftclick.lua", item+"_LC");
			}
			if (newconfigfile.contains("items."+item+".onEat.casts")) {
				newconfigfile.set("items."+item+".onEat.casts", null);
				newconfigfile.set("items."+item+".onEat.lua", item+"_EAT");
			}
		}
		
		//read old configfile to create lua scripts
		File luascript = new File(SpellItems.instance.getDataFolder(), oldconfig+"_converted.lua");
		try {
			luascript.createNewFile();
		} catch (IOException e1) {
		}
		ArrayList<SpellConvertFunction> spelllist = new ArrayList<SpellConvertFunction>();
		ConfigurationSection osection = configfile.getConfigurationSection("items");
		Set<String> okeys = osection.getKeys(false);
		for(String item : okeys) {
			if (configfile.contains("items."+item+".rightclick.casts")) {
				SpellConvertFunction func = new SpellConvertFunction(item+"_RC");
				func.load(configfile, "items."+item+".rightclick.casts");
				spelllist.add(func);
			}
			if (configfile.contains("items."+item+".leftclick.casts")) {
				SpellConvertFunction func = new SpellConvertFunction(item+"_LC");
				func.load(configfile, "items."+item+".leftclick.casts");
				spelllist.add(func);
			}
			if (configfile.contains("items."+item+".onEat.casts")) {
				SpellConvertFunction func = new SpellConvertFunction(item+"_EAT");
				func.load(configfile, "items."+item+".onEat.casts");
				spelllist.add(func);
			}
		}
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(luascript));
			for(SpellConvertFunction function : spelllist) {
				function.save(writer);
				writer.write("");
				writer.newLine();
			}
			writer.flush();
		} catch (IOException e1) {
		} finally {
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
		
		
		//save the new config file
		try {
			newconfigfile.save(new File(SpellItems.instance.getDataFolder(), oldconfig+"_converted.yml"));
		} catch (IOException e) {
		}
	}
}
