package me.andre111.items.item;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SpellVariable {
	public static final String BOOLEAN = "BOOLEAN";
	public static final String DOUBLE = "DOUBLE";
	public static final String STRING = "STRING";
	public static final String LOCATION = "LOCATION";
	public static final String PLAYER = "PLAYER";
	public static final String BLOCK = "BLOCK";
	
	private String type;
	private Object obj;
	
	public SpellVariable(String t, Object object) {
		type = t;
		obj = object;
	}
	public SpellVariable(String t, Double object) {
		type = t;
		obj = object;
	}
	
	public String getType() {
		return type;
	}
	public Object getObj() {
		return obj;
	}
	
	//Special getters
	public boolean getAsIntBoolean() {
		if(type.equalsIgnoreCase(BOOLEAN)) {
			return ((Boolean) obj).booleanValue();
		} else if(type.equalsIgnoreCase(DOUBLE)) {
			return ((Double) obj).doubleValue() == 1;
		} else if(type.equalsIgnoreCase(STRING)) {
			try {
				return Double.parseDouble((String) obj) == 1;
			} catch(NumberFormatException e) {
			}
		}
		
		return false;
	}
	
	public double getAsDouble() {
		if(type.equalsIgnoreCase(DOUBLE)) {
			return ((Double) obj).doubleValue();
		} else if(type.equalsIgnoreCase(STRING)) {
			try {
				return Double.parseDouble((String) obj);
			} catch(NumberFormatException e) {
			}
		} else {
			try {
				return Double.parseDouble(""+obj);
			} catch(NumberFormatException e) {
			}
		}
		
		return Double.NaN;
	}
	public int getAsInt() {
		return (int) Math.round(getAsDouble());
	}
	
	public String getAsString() {
		if(type.equalsIgnoreCase(STRING)) {
			return (String) obj;
		}
		
		return "";
	}
	
	public Location getAsLocation() {
		if(type.equalsIgnoreCase(LOCATION)) {
			return (Location) obj;
		}
		
		return null;
	}
	
	public Player getAsPlayer() {
		if(type.equalsIgnoreCase(PLAYER)) {
			return (Player) obj;
		} else if(type.equalsIgnoreCase(STRING)) {
			return Bukkit.getPlayerExact((String) obj);
		}
		
		return null;
	}
	
	public Block getAsBlock() {
		if(type.equalsIgnoreCase(BLOCK)) {
			return (Block) obj;
		}
		
		return null;
	}
}
