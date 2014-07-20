package me.andre111.items.item.spell;

import me.andre111.items.RewardManager;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.PlayerHandler;

import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemRewardPoints extends ItemSpell {
	/*private String playername = "";
	private int points = 1;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue pointsN = args.arg(2);
			
			if(playerN.isstring() && pointsN.isnumber()) {
				Player player = PlayerHandler.getPlayerFromUUID(playerN.toString());
				int points = pointsN.toint();
				
				if(player!=null) {
					if(points>0) {
						RewardManager.addRewardPoints(player, points);
					}
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
