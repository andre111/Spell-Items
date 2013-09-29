package me.andre111.items.world;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldThunderStorm extends WorldEffect {
	private Random rand;
	private int chance;
	private int distance;
	
	public WorldThunderStorm(int chance, int distance) {
		this.chance = chance;
		this.distance = distance;
	}
	
	@Override
	public void begin(int time, Location start) {
		rand = new Random();
		
		getWorld().setStorm(true);
		getWorld().setThundering(true);
		getWorld().setThunderDuration(time);
	}
	@Override
	public void tick() {
		if(rand.nextInt(100)<chance) {
			//select random player position
			List<Player> players = getWorld().getPlayers();
			Location loc = players.get(rand.nextInt(players.size())).getLocation().clone();
			
			//randomize distance
			loc.add(rand.nextInt(distance*2)-distance, rand.nextInt(distance*2)-distance, rand.nextInt(distance*2)-distance);
		
			//strike highest block
			loc = getWorld().getHighestBlockAt(loc).getLocation();
			
			//strike lightning
			loc.getWorld().strikeLightning(loc);
		}
	}
	@Override
	public void end() {
		getWorld().setStorm(false);
		getWorld().setThundering(false);
	}
}
