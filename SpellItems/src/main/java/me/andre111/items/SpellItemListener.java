package me.andre111.items;

import java.util.List;

import me.andre111.items.item.CustomItem;
import me.andre111.items.item.spell.ItemLaunch;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SpellItemListener implements Listener {
	public SpellItemListener(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	//custom enchantments
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		if(event.getEntity().getShooter() instanceof Player) {
			Player shooter = (Player) event.getEntity().getShooter();

			SpellItems.enchantManager.projectileShoot(shooter.getItemInHand(), event.getEntity());
		}
	}
	
	//custom enchants
	private boolean ignoreDamage = false;
	@EventHandler(priority=EventPriority.MONITOR)
	public void onEntityDamageEntityMonitor(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player)event.getEntity();

		if(event.getDamager() instanceof Player) {
			Player attacker = (Player) event.getDamager();
			SpellItems.enchantManager.attackPlayerByPlayer(attacker, player, attacker.getItemInHand());
			
			//"leftclicking"
			if (!ignoreDamage) {
				ignoreDamage = true;
				SpellItems.playerSpecialItemC(attacker, attacker.getItemInHand(), 0, null, player);
			} else {
				ignoreDamage = false;
			}
		}
		if(event.getDamager() instanceof Projectile) {
			Projectile a = (Projectile) event.getDamager();
			if(a.getShooter() instanceof Player) {
				Player attacker = (Player) a.getShooter();
				SpellItems.enchantManager.attackPlayerByProjectile(attacker, player, a);
			}
		}
	}
	
	@EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled()) return;
		
		if (event.getCause() == DamageCause.FALL && event.getEntity() instanceof Player && SpellItems.jumpingNormal.contains((Player)event.getEntity())) {
			event.setCancelled(true);
			SpellItems.jumpingNormal.remove((Player)event.getEntity());
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		Action action = event.getAction();
		ItemStack item = event.getItem();
		if(item==null) return;
		
		if (action==Action.RIGHT_CLICK_AIR) {
			SpellItems.playerSpecialItemC(player, item, 1, null, null);
		}
		else if(action==Action.RIGHT_CLICK_BLOCK) {
			SpellItems.playerSpecialItemC(player, item, 1, event.getClickedBlock(), null);
		}
		else if(action==Action.LEFT_CLICK_AIR) {
			SpellItems.playerSpecialItemC(player, item, 0, null, null);
		}
		else if(action==Action.LEFT_CLICK_BLOCK) {
			SpellItems.playerSpecialItemC(player, item, 0, event.getClickedBlock(), null);
		}
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
			
		Entity entity = event.getRightClicked();
		ItemStack item = event.getPlayer().getItemInHand();
		if(entity instanceof Player) {
			SpellItems.playerSpecialItemC(player, item, 1, null, (Player)entity);
		}
	}
	
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		SpellItems.playerSpecialItemC(player, item, 2, null, null);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		//specialitems - block place disable
		ItemStack item = event.getItemInHand();
		/*ItemMeta im = item.getItemMeta();
		if(im!=null) {
			if(im.hasDisplayName()) {*/
				List<CustomItem> cil = SpellItems.itemManager.getItemByAtrribute(item);
				if(cil!=null) {
					for(int i=0; i<cil.size(); i++) {
						CustomItem ci = cil.get(i);

						if(ci.isThisItem(item)) {
							if(!ci.isAllowPlace()) {
								event.setCancelled(true);
							}
						}
					}
				}
			/*}
		}*/
	}
	
	//fallingsand
	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		if(event.getEntityType()==EntityType.FALLING_BLOCK) {
			FallingBlock entity = (FallingBlock) event.getEntity();
			//falling sand hit
			if(entity.hasMetadata("spellitems_falling_casting")) {
				ItemLaunch il = (ItemLaunch) entity.getMetadata("spellitems_falling_casting").get(0).value();
				String playern = entity.getMetadata("spellitems_falling_playername").get(0).asString();

				//il.onHit(Bukkit.getServer().getPlayerExact(playern), event.getBlock());
			}
			//disable blocks from fallingsand
			if(entity.hasMetadata("spellitems_falling_noblock")) {
				event.setCancelled(true);
			}
		}
	}
	
	//manasystem on sneak?
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		if(event.isCancelled()) return;

		Player p  = event.getPlayer();

		if(event.isSneaking()) {
			StatManager.show(p);
		} else {
			StatManager.hide(p, false);
		}
	}
	//update xpbarstat
	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent event) {
		Player p  = event.getPlayer();
		StatManager.updateXPBarStat(p);
	}
	//update xp
	@EventHandler
	public void onPlayerInventoryOpen(InventoryOpenEvent event) {
		if(event.isCancelled()) return;

		Player p  = (Player) event.getPlayer();
		StatManager.onInventoryOpen(p);
	}
	@EventHandler
	public void onPlayerInventoryClose(InventoryCloseEvent event) {
		Player p  = (Player) event.getPlayer();
		StatManager.onInventoryClose(p);
	}
	//update upcounters
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerItemHeld(PlayerItemHeldEvent event) {
		if(event.isCancelled()) return;

		StatManager.interruptItem(event.getPlayer().getUniqueId());
	}
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerInventoryChange(InventoryClickEvent event) {
		if(event.isCancelled()) return;

		Player p = (Player) event.getWhoClicked();
		if(event.getSlot()==p.getInventory().getHeldItemSlot()) {
			StatManager.interruptDamage(p.getUniqueId());
		}
	}
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {
		if(event.isCancelled()) return;

		try {
			if(event.getFrom().distanceSquared(event.getTo())>0.01)
				StatManager.interruptMove(event.getPlayer().getUniqueId());
		} catch(Exception e) {
		}
	}
}
