package me.andre111.items.item.spell;

import java.util.ArrayList;

import me.andre111.items.item.ItemSpell;

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
	public boolean cast(Player player) {
		boolean succed = false;
		for(String st : commands) {
			if(!st.contains("-1-")) {
				String command = st.replace("-0-", player.getName());
				succed = true;
				if(console)
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
				else
					Bukkit.getServer().dispatchCommand(player, command);
			}
		}
		return succed;
	}
	@Override
	public boolean cast(Player player, Block block) {
		boolean succed = false;
		for(String st : commands) {
			if(!st.contains("-1-")) {
				String command = st.replace("-0-", player.getName());
				succed = true;
				if(console)
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
				else
					Bukkit.getServer().dispatchCommand(player, command);
			}
		}
		return succed;
	}
	@Override
	public boolean cast(Player player, Player target) {
		boolean succed = false;
		for(String st : commands) {
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
	@Override
	//casted by another spell on that location
	public boolean cast(Player player, Location loc) {
		boolean succed = false;
		for(String st : commands) {
			if(!st.contains("-1-")) {
				String command = st.replace("-0-", player.getName());
				succed = true;
				if(console)
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
				else
					Bukkit.getServer().dispatchCommand(player, command);
			}
		}
		return succed;
	}
}
