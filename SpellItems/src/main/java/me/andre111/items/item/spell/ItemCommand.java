package me.andre111.items.item.spell;

import java.util.UUID;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemCommand extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		boolean succed = false;
		
		if(args.narg()>=2) {
			LuaValue playerN = LUAHelper.getInternalValue(args.arg(1));
			LuaValue consoleN = args.arg(2);
			
			if((playerN.isuserdata(UUID.class) || (consoleN.isboolean() && consoleN.toboolean())) && consoleN.isboolean()) {
				Entity target = EntityHandler.getEntityFromUUID((UUID) playerN.touserdata(UUID.class));
				boolean console = consoleN.toboolean();
				
				CommandSender sender = target;
				if(console) sender = Bukkit.getConsoleSender();
				
				if(sender!=null) {
					succed = true;
					
					int pos = 3;
					while(args.narg()>=pos) {
						LuaValue commandN = args.arg(pos);
						
						if(commandN.isstring()) {
							Bukkit.dispatchCommand(sender, commandN.toString());
						}
						
						pos++;
					}
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		if(succed)
			return RETURN_TRUE;
		else
			return RETURN_FALSE;
	}
}
