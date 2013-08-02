package me.andre111.items.utils;

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
}
