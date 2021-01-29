package fr.aimlisse.games.events;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import fr.aimlisse.games.HGMain;
import fr.aimlisse.games.util.GameStatus;

public class SpectatorEvent implements Listener {
	
	private HGMain main;

    public SpectatorEvent(HGMain hgMain) {
        this.main = hgMain;
    }
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (this.main.getStatus() == GameStatus.IN) {
			this.main.setSpectator(player);

		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		this.main.spectators.remove(player);
		
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player damager = (Player) event.getPlayer();
		if(this.main.spectators.contains(damager)) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onEntityTarget(EntityTargetEvent event){
		Entity e = event.getEntity();
			Player player = (Player) event.getTarget();
			if(this.main.spectators.contains(player) || this.main.getStatus() != GameStatus.IN) {
				event.setCancelled(true);
				event.setTarget(null);
			}
			
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onTakeDamage(EntityDamageByEntityEvent event) {
		if(this.main.spectators.contains(event.getDamager()) || this.main.getStatus() != GameStatus.IN) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if(this.main.spectators.contains(player) || this.main.getStatus() != GameStatus.IN) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if(this.main.spectators.contains(player) || this.main.getStatus() != GameStatus.IN) {
			event.setCancelled(true);
		}
		
		
		
		
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player victim = (Player) event.getEntity();
			if(event.getCause() == DamageCause.VOID) {
				if(this.main.spectators.contains(victim)) {
					event.setCancelled(true);
				}
			if(this.main.getStatus() != GameStatus.IN) {
				event.setCancelled(true);
			}
			if(event.getCause() == DamageCause.VOID) {
				event.setCancelled(true);
				if(this.main.getStatus() != GameStatus.IN) {
					victim.teleport(new Location(Bukkit.getWorld("world"), 0, 110, 0, 0, 0));
				}else {
					victim.damage(20);
				}
			}
			}
		}

	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(this.main.spectators.contains(player)) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlaceBreak(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(this.main.spectators.contains(player)) {
			event.setCancelled(true);
		}
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if(this.main.spectators.contains(player)) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		event.setDroppedExp(0);
		event.setDeathMessage(null);
		event.setKeepInventory(false);
		player.getInventory().clear();
		player.setHealth(20);
		if(player.getKiller() instanceof Player) {
			Player killer = (Player) event.getEntity().getKiller();
			Bukkit.broadcastMessage("§c§l⚔ §b" + player.getDisplayName() + "§7 s'est fait tuer par §b" + killer.getDisplayName());
		}else {
			Bukkit.broadcastMessage("§c§l⚔ §b" + player.getDisplayName() + "§7 s'est suicidé");
		}
		player.playSound(player.getLocation(), Sound.HORSE_DEATH, 1, 1);
		player.teleport(new Location(Bukkit.getWorld("world"), 0, 110, 0, 0, 0));
		player.getInventory().clear();
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setLevel(0);
		player.setExp(0);
		 if(this.main.isStarted() != true) return;
	        if(this.main.isStarted() == true) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				
					if (!players.getActivePotionEffects().contains(PotionEffectType.INVISIBILITY)) {
						Bukkit.broadcastMessage("§d§l " + players.getName() + " §6§lRemporte la victoire !");
						Bukkit.getScheduler().runTaskLater(this.main, () -> {
							ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
							String command = "restart";
							Bukkit.dispatchCommand(console, command);
						}, 3600L);
					}
				}
			}
		player.sendMessage("§c§lVOUS ETES MORT! §eVous êtes désormais un spectateur.");
		this.main.setSpectator(player);
		
		
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
	Player player = (Player) event.getEntity();
	if(this.main.spectators.contains(player) || this.main.getStatus() != GameStatus.IN) {
		event.setCancelled(true);
	}
	
	
}}
