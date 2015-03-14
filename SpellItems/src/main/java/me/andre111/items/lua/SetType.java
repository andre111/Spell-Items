package me.andre111.items.lua;

import me.andre111.items.volatileCode.DeprecatedMethods;

import org.bukkit.block.Block;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class SetType extends VarArgFunction {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=3) {
			LuaValue value = LUAHelper.getInternalValue(args.arg(1));
			LuaValue type = args.arg(2);
			LuaValue data = args.arg(3);
			
			if(value.isuserdata(Block.class) && type.isstring() && data.isnumber()) {
				Block block = (Block) value.touserdata(Block.class);
				
				block.setType(DeprecatedMethods.getMaterialFromInternalName(type.tojstring()));
				DeprecatedMethods.setBlockData(block, data.tobyte());
				
				return LuaValue.TRUE;
			}
		}
		
		return LuaValue.FALSE;
	}
}
