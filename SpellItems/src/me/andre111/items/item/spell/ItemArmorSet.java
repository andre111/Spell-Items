package me.andre111.items.item.spell;

import me.andre111.items.ItemHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemArmorSet extends ItemSpell {
	/*private String playername = "";
	
	private String helmet = "";
	private String chest = "";
	private String leggins = "";
	private String boots = "";
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==0) playername = var;
		else if(id==1) helmet = var;
		else if(id==2) chest = var;
		else if(id==3) leggins = var;
		else if(id==4) boots = var;
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) playername = var.getAsString();
		else if(id==1) helmet = var.getAsString();
		else if(id==2) chest = var.getAsString();
		else if(id==3) leggins = var.getAsString();
		else if(id==4) boots = var.getAsString();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Player pTarget = Bukkit.getPlayerExact(playername);
		if(playername.equals("")) {
			pTarget = player;
		}
		
		if(pTarget!=null) {
			setArmor(pTarget);
			return true;
		}
		
		return false;
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=1) {
			LuaValue playerN = args.arg(1);

			if(playerN.isstring()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				
				if(player!=null) {
					String helmet = "";
					String chest = "";
					String leggins = "";
					String boots = "";
					
					if(args.narg()>=2 && args.arg(2).isstring()) helmet = args.arg(2).toString();
					if(args.narg()>=3 && args.arg(3).isstring()) helmet = args.arg(3).toString();
					if(args.narg()>=4 && args.arg(4).isstring()) helmet = args.arg(4).toString();
					if(args.narg()>=5 && args.arg(5).isstring()) helmet = args.arg(5).toString();
					
					setArmor(player, helmet, chest, leggins, boots);
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	private void setArmor(Player player, String helmet, String chest, String leggins, String boots) {
		ItemStack helmetIt = ItemHandler.decodeItem(helmet);
		if(helmetIt!=null) player.getInventory().setHelmet(helmetIt);
		
		ItemStack chestIt = ItemHandler.decodeItem(chest);
		if(chestIt!=null) player.getInventory().setChestplate(chestIt);
		
		ItemStack legginsIt = ItemHandler.decodeItem(leggins);
		if(legginsIt!=null) player.getInventory().setLeggings(legginsIt);
		
		ItemStack bootsIt = ItemHandler.decodeItem(boots);
		if(bootsIt!=null) player.getInventory().setBoots(bootsIt);
	}
}
