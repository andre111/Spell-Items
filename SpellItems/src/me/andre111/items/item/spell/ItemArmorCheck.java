package me.andre111.items.item.spell;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemArmorCheck extends ItemSpell {
	/*private String playername = "";
	private boolean shouldHave = false;
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==0) playername = var;
	}
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==1) shouldHave = (var==1);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) playername = var.getAsString();
		else if(id==1) shouldHave = var.getAsIntBoolean();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Player pTarget = Bukkit.getPlayerExact(playername);
		if(playername.equals("")) {
			pTarget = player;
		}
		
		if(pTarget!=null) {
			if(ItemHandler.isArmorEmpty(pTarget)) {
				if(shouldHave) return false;
				else return true;
			} else {
				if(!shouldHave) return false;
				else return true;
			}
		}
		
		return false;
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue armorN = args.arg(2);
			
			if(playerN.isstring() && armorN.isboolean()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				boolean shouldHave = armorN.toboolean();
				
				if(player!=null) {
					if(ItemHandler.isArmorEmpty(player)) {
						if(shouldHave) return RETURN_FALSE;
						else return RETURN_TRUE;
					} else {
						if(!shouldHave) return RETURN_FALSE;
						else return RETURN_TRUE;
					}
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
