package me.andre111.items.config;

import java.io.File;
import java.io.IOException;

import me.andre111.items.SpellItems;
import me.andre111.items.utils.FileHandler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	private static FileConfiguration configfile;
	private static FileConfiguration itemfile;
	
	public static void initConfig() {
		exportConfigs();
		
		if (!new File(SpellItems.instance.getDataFolder(), "config.yml").exists()) {
			try {
				FileHandler.copyFolder(new File(SpellItems.instance.getDataFolder(), "config/default/config.yml"), new File(SpellItems.instance.getDataFolder(), "config.yml"));
			} catch (IOException e) {}
		}
		if (!new File(SpellItems.instance.getDataFolder(), "items.yml").exists()) {
			try {
				FileHandler.copyFolder(new File(SpellItems.instance.getDataFolder(), "config/default/items.yml"), new File(SpellItems.instance.getDataFolder(), "items.yml"));
			} catch (IOException e) {}
		}
		configfile = YamlConfiguration.loadConfiguration(new File(SpellItems.instance.getDataFolder(), "config.yml"));
		itemfile = YamlConfiguration.loadConfiguration(new File(SpellItems.instance.getDataFolder(), "items.yml"));
	}
	
	private static void exportConfigs() {
		SpellItems.instance.saveResource("config/default/config.yml", true);
		SpellItems.instance.saveResource("config/default/items.yml", true);
	}

	public static void reloadConfig() {
		itemfile = YamlConfiguration.loadConfiguration(new File(SpellItems.instance.getDataFolder(), "items.yml"));
	}
	
	public static FileConfiguration getItemFile() {
		return itemfile;
	}
	public static FileConfiguration getStaticConfig() {
		return configfile;
	}
}
