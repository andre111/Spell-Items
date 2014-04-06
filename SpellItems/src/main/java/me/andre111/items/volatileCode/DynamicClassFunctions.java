package me.andre111.items.volatileCode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;

import me.andre111.items.SpellItems;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;

public class DynamicClassFunctions {
	public static String nmsPackage = "net.minecraft.server.v1_7_R1";
	public static String obcPackage = "org.bukkit.craftbukkit.v1_7_R1";
	
	public static boolean setPackages() {
		Server craftServer = Bukkit.getServer();
		if (craftServer != null) {
			try {
				Class<?> craftClass = craftServer.getClass();
				Method getHandle = craftClass.getMethod("getHandle");
				Class<?> returnType = getHandle.getReturnType();

				obcPackage = craftClass.getPackage().getName();
				nmsPackage = returnType.getPackage().getName();
				return true;
			} catch (Exception e) {
			}
		}
		return false;
	}
	
	public static HashMap<String, Class<?>> classes = new HashMap<String, Class<?>>();
	public static boolean setClasses() {
		try {
			// org.bukkit.craftbukkit
			classes.put("CraftItemStack", Class.forName(obcPackage + ".inventory.CraftItemStack"));
			classes.put("CraftFallingSand", Class.forName(obcPackage + ".entity.CraftFallingSand"));
			
			// net.minecraft.server
			classes.put("ItemStack", Class.forName(nmsPackage + ".ItemStack"));
			classes.put("NBTTagCompound", Class.forName(nmsPackage + ".NBTTagCompound"));
			classes.put("NBTTagList", Class.forName(nmsPackage + ".NBTTagList"));
			classes.put("NBTBase", Class.forName(nmsPackage + ".NBTBase"));
			classes.put("EntityFallingBlock", Class.forName(nmsPackage + ".EntityFallingBlock"));
			
			return true;
		} catch (Exception e) {
			SpellItems.logger.log(Level.SEVERE, "Could not aquire a required class", e);
			return false;
		}
	}
	
	public static HashMap<String, Method> methods = new HashMap<String, Method>();
	public static boolean setMethods() {
		try {
			// org.bukkit.craftbukkit
			methods.put("CraftItemStack.asNMSCopy(item)", classes.get("CraftItemStack").getDeclaredMethod("asNMSCopy", ItemStack.class));
			methods.put("CraftItemStack.asCraftMirror(item)", classes.get("CraftItemStack").getDeclaredMethod("asCraftMirror", classes.get("ItemStack")));
			methods.put("CraftFallingSand.getHandle()", classes.get("CraftFallingSand").getDeclaredMethod("getHandle"));
			
			// net.minecraft.server
			methods.put("ItemStack.hasTag()", classes.get("ItemStack").getDeclaredMethod("hasTag"));
			methods.put("ItemStack.setTag(nbttagcompound)", classes.get("ItemStack").getDeclaredMethod("setTag", classes.get("NBTTagCompound")));
			methods.put("ItemStack.getTag()", classes.get("ItemStack").getDeclaredMethod("getTag"));
			methods.put("NBTTagCompound.set(name, tag)", classes.get("NBTTagCompound").getDeclaredMethod("set", String.class, classes.get("NBTBase")));
			
			return true;
		} catch (Exception e) {
			SpellItems.logger.log(Level.SEVERE, "Could not find a required method", e);
			return false;
		}
	}
	
	public static HashMap<String, Field> fields = new HashMap<String, Field>();
	public static boolean setFields() {
		try {
			fields.put("EntityFallingBlock.hurtEntities", classes.get("EntityFallingBlock").getDeclaredField("hurtEntities"));
			fields.put("EntityFallingBlock.fallHurtAmount", classes.get("EntityFallingBlock").getDeclaredField("fallHurtAmount"));
			fields.put("EntityFallingBlock.fallHurtMax", classes.get("EntityFallingBlock").getDeclaredField("fallHurtMax"));
			return true;
		} catch (Exception e) {
			SpellItems.logger.log(Level.SEVERE, "Could not find a field class", e);
			return false;
		}
	}
	
	public static ItemStack addGlow(ItemStack item) {
		try {
			Object nmsStack = methods.get("CraftItemStack.asNMSCopy(item)").invoke(null, item);

			Object tag = null;
			if (!methods.get("ItemStack.hasTag()").invoke(nmsStack).equals(true)){
				tag = classes.get("NBTTagCompound").newInstance();
				methods.get("ItemStack.setTag(nbttagcompound)").invoke(nmsStack, tag);
			}
			if (tag == null)
				tag = methods.get("ItemStack.getTag()").invoke(nmsStack);

			Object ench = classes.get("NBTTagList").newInstance();
			methods.get("NBTTagCompound.set(name, tag)").invoke(tag, "ench", ench);
			methods.get("ItemStack.setTag(nbttagcompound)").invoke(nmsStack, tag);

			return (ItemStack) methods.get("CraftItemStack.asCraftMirror(item)").invoke(null, nmsStack);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}
	
	public static void setFallingBlockHurtEntities(FallingBlock block, float damage, int max) {
		try {
			Object efb = methods.get("CraftFallingSand.getHandle()").invoke(block);
			
			Field field = fields.get("EntityFallingBlock.hurtEntities");
			field.setAccessible(true);
			field.setBoolean(efb, true);
			
			field = fields.get("EntityFallingBlock.fallHurtAmount");
			field.setAccessible(true);
			field.setFloat(efb, damage);

			field = fields.get("EntityFallingBlock.fallHurtMax");
			field.setAccessible(true);
			field.setInt(efb, max);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
