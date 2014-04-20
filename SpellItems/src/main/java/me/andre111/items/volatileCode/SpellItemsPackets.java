package me.andre111.items.volatileCode;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

import me.andre111.items.SpellItems;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;

public class SpellItemsPackets {
	public static void createParticle(Location effectPos, String type) {
		PacketContainer pc = new PacketContainer(PacketType.Play.Server.WORLD_PARTICLES);
		pc.getStrings().write(0, type);
		pc.getFloat().
		write(0, (float)effectPos.getX()).
		write(1, (float)effectPos.getY()).
		write(2, (float)effectPos.getZ()).
		write(3, 0.5f).
		write(4, 0.5f).
		write(5, 0.5f).
		write(6, 0f);
		pc.getIntegers().write(0, 10);

		//Create explosion effect at location
		for(Player p : effectPos.getWorld().getPlayers()) {
			try {
				ProtocolLibrary.getProtocolManager().sendServerPacket(p, pc);
			} catch (InvocationTargetException e) {
			}
		}
	}

	public static void sendFakeXP(final Player player, final int level, final float xp) {
		Bukkit.getScheduler().runTaskLater(SpellItems.instance, new Runnable() {
			public void run() {
				final PacketContainer fakeXPChange = SpellItems.protocolManager.createPacket(PacketType.Play.Server.EXPERIENCE);
				
				fakeXPChange.getFloat().
				write(0, xp);
				fakeXPChange.getIntegers().
				write(0, 0).
				write(1, level);


				try {
					if(player.isOnline())
						SpellItems.protocolManager.sendServerPacket(player, fakeXPChange);
				} catch (Exception e) {
				}
			}
		}, 1);
	}
	public static void sendRealXP(final Player player) {
		Bukkit.getScheduler().runTaskLater(SpellItems.instance, new Runnable() {
			public void run() {
				final PacketContainer fakeXPChange = SpellItems.protocolManager.createPacket(PacketType.Play.Server.EXPERIENCE);
				
				fakeXPChange.getFloat().
				write(0, player.getExp());
				fakeXPChange.getIntegers().
				write(0, player.getTotalExperience()).
				write(1, player.getLevel());

				try {
					if(player.isOnline())
						SpellItems.protocolManager.sendServerPacket(player, fakeXPChange);
				} catch (Exception e) {
				}
			}
		}, 1);
	}

	//Packet Listeners
	public static ArrayList<UUID> disabledExits = new ArrayList<UUID>();
	public static void initPacketListeners() {
		SpellItems.protocolManager.addPacketListener(new PacketAdapter(SpellItems.instance,
				ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
			 @Override
			    public void onPacketReceiving(PacketEvent event) {
			    	Player player = event.getPlayer();
			    	
			    	try {
		            	PacketContainer packet = event.getPacket();

		            	boolean exit = packet.getBooleans().read(1);
		                if(exit && disabledExits.contains(player.getUniqueId())) {
		                	event.setCancelled(true);
		                }

		            } catch (FieldAccessException e) {
		                SpellItems.log("Couldn't access a field in an 0x0C-SteerVehicle packet!");
		            }
			 }
		});
	}
}
