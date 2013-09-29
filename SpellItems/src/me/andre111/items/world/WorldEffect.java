package me.andre111.items.world;

import java.util.UUID;

import me.andre111.items.SpellItems;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class WorldEffect {
	private UUID world;
	private int time;
	private int task;
	
	//methods to override
	public void begin(int time, Location start) {
	}
	public void tick() {
	}
	public void end() {
	}
	
	//internal starter
	public void start(World w, Location start, int ticks) {
		world = w.getUID();
		time = ticks;
		begin(time, start);
		task = Bukkit.getScheduler().runTaskTimer(SpellItems.instance, new Runnable() {
			private int ticker = 0;
			
			@Override
			public void run() {
				if(getWorld()==null) return;
				
				ticker += 1;
				tick();
				
				if(ticker>=time) {
					end();
					stop();
				}
			}
		}, 1, 1).getTaskId();
	}
	public void stop() {
		Bukkit.getScheduler().cancelTask(task);
	}
	
	protected World getWorld() {
		return Bukkit.getServer().getWorld(world);
	}
}
