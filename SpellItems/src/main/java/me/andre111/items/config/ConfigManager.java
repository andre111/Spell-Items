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
	private static FileConfiguration rewardfile;
	
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
		if (!new File(SpellItems.instance.getDataFolder(), "rewards.yml").exists()) {
			try {
				FileHandler.copyFolder(new File(SpellItems.instance.getDataFolder(), "config/default/rewards.yml"), new File(SpellItems.instance.getDataFolder(), "rewards.yml"));
			} catch (IOException e) {}
		}
		if (!new File(SpellItems.instance.getDataFolder(), "spells.lua").exists()) {
			try {
				FileHandler.copyFolder(new File(SpellItems.instance.getDataFolder(), "config/default/spells.lua"), new File(SpellItems.instance.getDataFolder(), "spells.lua"));
			} catch (IOException e) {}
		}
		configfile = SIFileConfiguration.loadConfiguration(new File(SpellItems.instance.getDataFolder(), "config.yml"));
		itemfile = SIFileConfiguration.loadConfiguration(new File(SpellItems.instance.getDataFolder(), "items.yml"));
		rewardfile = SIFileConfiguration.loadConfiguration(new File(SpellItems.instance.getDataFolder(), "rewards.yml"));
	}
	
	private static void exportConfigs() {
		SpellItems.instance.saveResource("config/default/config.yml", true);
		SpellItems.instance.saveResource("config/default/items.yml", true);
		SpellItems.instance.saveResource("config/default/rewards.yml", true);
		SpellItems.instance.saveResource("config/default/spells.lua", true);
	}

	public static void reloadConfig() {
		itemfile = YamlConfiguration.loadConfiguration(new File(SpellItems.instance.getDataFolder(), "items.yml"));
		rewardfile = YamlConfiguration.loadConfiguration(new File(SpellItems.instance.getDataFolder(), "rewards.yml"));
	}
	
	public static FileConfiguration getItemFile() {
		return itemfile;
	}
	public static FileConfiguration getRewardFile() {
		return rewardfile;
	}
	public static FileConfiguration getStaticConfig() {
		return configfile;
	}
}
