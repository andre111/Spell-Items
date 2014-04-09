package me.andre111.items.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerHandler {
	public static boolean hasHigherPotionEffect(Player player, int id, int level) {
		if(player.hasPotionEffect(PotionEffectType.getById(id))) {
			PotionEffect[] effects = (PotionEffect[]) player.getActivePotionEffects().toArray(new PotionEffect[player.getActivePotionEffects().size()]);
			for(int i=0; i<effects.length; i++) {
				if(effects[i].getType()==PotionEffectType.getById(id)) {
					if(effects[i].getAmplifier()>level) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static Player getPlayerFromUUID(String uuid) {
		return getPlayerFromUUID(UUID.fromString(uuid));
	}
	public static Player getPlayerFromUUID(UUID uuid) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getUniqueId().equals(uuid)) {
				return player;
			}
		}
		
		return null;
	}
}
