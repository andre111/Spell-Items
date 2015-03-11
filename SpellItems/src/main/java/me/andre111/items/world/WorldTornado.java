package me.andre111.items.world;

import java.util.Random;

import me.andre111.items.SpellItems;
import me.andre111.items.volatileCode.DeprecatedMethods;
import me.andre111.items.volatileCode.DynamicClassFunctions;
import me.andre111.items.volatileCode.SpellItemsPackets;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

public class WorldTornado extends WorldEffect {
	private Location loc;
	private int ticker;
	
	private double moveSpeed;
	private int direction = -1;
	private int changeChance;
	
	private int blockChance;
	private int radius;
	private boolean hurt;

	private Random rand;
	
	public WorldTornado(double movespeed, int changec, int blockc, int bradius, boolean h) {
		moveSpeed = movespeed;
		changeChance = changec;
		blockChance = blockc;
		radius = bradius;
		hurt = h;
	}
	
	@Override
	public void begin(int time, Location start) {
		loc = start;
		ticker = 0;
		
		direction = -1;
		
		rand = new Random();
	}
	@Override
	public void tick() {
		//move
		if(direction==-1) direction = rand.nextInt(8);
		if(rand.nextInt(100)<changeChance) direction = rand.nextInt(8);
		//left/right/...
		double xP = 0;
		double zP = 0;
		//X
		if(direction==0 || direction==1 || direction==7) {
			xP = moveSpeed;
		} else if(direction==3 || direction==4 || direction==5) {
			xP = -moveSpeed;
		}
		//Z
		if(direction==1 || direction==2 || direction==3) {
			zP = moveSpeed;
		} else if(direction==5 || direction==6 || direction==7) {
			zP = -moveSpeed;
		}
		loc = loc.add(xP, 0, zP);
		//up/down
		if(loc.getBlockY()<loc.getWorld().getHighestBlockYAt(loc)) {
			loc = loc.add(0, moveSpeed*2, 0);
		} else if(loc.getBlockY()>loc.getWorld().getHighestBlockYAt(loc)) {
			loc = loc.add(0, -moveSpeed*2, 0);
		}
		
		handleBlocks();
		createEffects();
	}
	@Override
	public void end() {
	}
	
	private void handleBlocks() {
		if(rand.nextInt(100)>=blockChance) return;
		
		//throw blocks or --rotate them(maybe difficult)?
		Location blockLoc = loc.clone();
		blockLoc.add(rand.nextInt(radius*2)-radius, rand.nextInt(radius*2)-radius, rand.nextInt(radius*2)-radius);
		blockLoc = blockLoc.getWorld().getHighestBlockAt(blockLoc).getLocation();
		while(blockLoc.getBlock().getType()==Material.AIR && blockLoc.getBlockY()>0) {
			blockLoc.setY(blockLoc.getBlockY()-1);
		}
		//if(Math.abs(blockLoc.getBlockY()-loc.getBlockY())>radius) return;
		Block b = blockLoc.getBlock();
		
		if(b.getType()!=Material.AIR && !SpellItems.isUntachable(b.getType())) {
			FallingBlock fb = DeprecatedMethods.spawnFallingBlock(loc, b.getType(), DeprecatedMethods.getBlockData(b));
			double power = 2.0;
			fb.setVelocity(new Vector(rand.nextDouble()*power-power/2, power, rand.nextDouble()*power-power/2));
			fb.setDropItem(false);
			if(hurt) {
				DynamicClassFunctions.setFallingBlockHurtEntities(fb, 0.25f, 2);
			}
			
			b.setType(Material.AIR);
			DeprecatedMethods.setBlockData(b, (byte)0);
		}
	}
	private void createEffects() {
		ticker += 1;
		
		if(ticker%5==0) {
			//"tornado" - many explosion effects on top of each other
			Location effectPos = loc.clone();
			int size = 10;
			for(int i=0; i<size; i++) {
				effectPos.add(0, 1.75+rand.nextDouble(), 0);
				
				if(rand.nextInt(10)==0) {
					SpellItemsPackets.createParticle(effectPos, "explode");
				} else {
					SpellItemsPackets.createParticle(effectPos, "largeexplode");
				}
				//top
				if(i==size-1) {
					SpellItemsPackets.createParticle(effectPos, "hugeexplosion");
					
					Location effectTop = effectPos.clone();
					effectTop = effectTop.add(4, 0, 0);
					SpellItemsPackets.createParticle(effectTop, "hugeexplosion");
					effectTop = effectTop.add(-8, 0, 0);
					SpellItemsPackets.createParticle(effectTop, "hugeexplosion");
					effectTop = effectTop.add(4, 0, 4);
					SpellItemsPackets.createParticle(effectTop, "hugeexplosion");
					effectTop = effectTop.add(0, 0, -8);
					SpellItemsPackets.createParticle(effectTop, "hugeexplosion");
				}
			}
			//Sound
			if(ticker>=10) {
				ticker = 0;
				
				//TODO - Fix sound
				//if(rand.nextInt(6)!=0)
					//loc.getWorld().playSound(loc, Sound.BREATH, 1f, 0.3f+rand.nextFloat()/4f);
			}
		}
	}
}
