package me.andre111.items.volatileCode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffectType;

//TODO - get rid of any usage of these methods
@SuppressWarnings("deprecation")
public class DeprecatedMethods {
	public static Player getPlayerByName(String name) {
		return Bukkit.getPlayerExact(name);
	}
	
	public static byte getDatavalue(MaterialData data) {
		return data.getData();
	}
	
	public static int getBlockID(Block block) {
		return block.getTypeId();
	}
	
	public static byte getBlockData(Block block) {
		return block.getData();
	}
	
	public static void setBlockData(Block block, byte data) {
		block.setData(data);
	}
	
	public static void setBlockIDandData(Block block, int id, byte data) {
		block.setTypeIdAndData(id, data, false);
	}
	
	public static int getGameModeValue(GameMode mode) {
		return mode.getValue();
	}
	
	public static Material getMaterialByID(int id) {
		return Material.getMaterial(id);
	}
	
	public static PotionEffectType getPotionEffectByID(int id) {
		return PotionEffectType.getById(id);
	}
	
	public static FallingBlock spawnFallingBlock(Location loc, Material mat, byte data) {
		return loc.getWorld().spawnFallingBlock(loc, mat, data);
	}
	
	//TODO - the getUnsafe methods should be replaced when they get allternatives
	public static Material getMaterialFromInternalName(String name) {
		return Bukkit.getUnsafe().getMaterialFromInternalName(name);
	}
	
	public static List<String> tabCompleteInternalName(String name) {
		return Bukkit.getUnsafe().tabCompleteInternalMaterialName(name, new ArrayList<String>());
	}

	public static ItemStack modifyItemStack(ItemStack it, String dataTag) {
		return Bukkit.getUnsafe().modifyItemStack(it, dataTag);
	}

	//this is a workaround and sometimes simply needed
	public static void updateInventory(Player player) {
		player.updateInventory();
	}
}
