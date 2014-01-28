package me.andre111.items.item;

public abstract class ItemVariableHelper {
	//returns true when its a boolean/string with "true"/integer with 1
	public static boolean getVariableAndIntegerBoolean(Object var) {
		if(var instanceof Boolean) {
			return (Boolean) var;
		}
		if(var instanceof String) {
			try {
				return Integer.parseInt((String)var) == 1;
			} catch(NumberFormatException e) {
				return Boolean.parseBoolean((String)var);
			}
		}
		if(var instanceof Integer) {
			return ((Integer) var).intValue() == 1;
		}
		
		return false;
	}
	
	public static String getVariableAsString(Object var) {
		return ""+var;
	}
	
	public static int getVariableAsInt(Object var) {
		return (int) Math.round(getVariableAsDouble(var));
	}
	public static double getVariableAsDouble(Object var) {
		if(var instanceof Double) {
			return ((Double) var).doubleValue();
		}
		if(var instanceof String) {
			try {
				return Double.parseDouble((String) var);
			} catch(NumberFormatException e) {
			}
		}
		
		return Double.NaN;
	}
}
