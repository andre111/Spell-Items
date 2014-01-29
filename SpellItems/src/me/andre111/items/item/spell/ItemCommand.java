package me.andre111.items.item.spell;

import java.util.ArrayList;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ItemCommand extends ItemSpell {
	private boolean console = false;
	private ArrayList<String> commands = new ArrayList<String>();
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) console = var==1;
	}
	@Override
	public void setCastVar(int id, String var) {
		if(id>0) commands.add(var);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) console = var.getAsIntBoolean();
		else commands.add(var.getAsString());
	}

	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		boolean succed = false;
		for(String st : commands) {
			if(player==null && st.contains("-0-")) continue;
			if(target==null && st.contains("-1-")) continue;
			
			String command = st.replace("-0-", player.getName());
			command = command.replace("-1-", target.getName());
			succed = true;
			if(console)
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
			else
				Bukkit.getServer().dispatchCommand(player, command);
		}
		return succed;
	}
}
