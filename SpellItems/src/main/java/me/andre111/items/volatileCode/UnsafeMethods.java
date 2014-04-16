package me.andre111.items.volatileCode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UnsafeMethods {
	//TODO - replace Unsafe methods when alternatives exist
	public static Material getMaterialFromInternalName(String name) {
		return Bukkit.getUnsafe().getMaterialFromInternalName(name);
	}

	public static ItemStack modifyItemStack(ItemStack it, String dataTag) {
		return Bukkit.getUnsafe().modifyItemStack(it, dataTag);
	}

	//TODO - remove temporary workaround
	@SuppressWarnings("deprecation")
	public static void updateInventory(Player player) {
		player.updateInventory();
	}
}
