package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemCommand extends ItemSpell {
	/*private boolean console = false;
	private ArrayList<String> commands = new ArrayList<String>();*/
	
	@Override
	public Varargs invoke(Varargs args) {
		boolean succed = false;
		
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue consoleN = args.arg(2);
			
			if(playerN.isstring() && consoleN.isboolean()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				boolean console = consoleN.toboolean();
				
				CommandSender sender = player;
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
