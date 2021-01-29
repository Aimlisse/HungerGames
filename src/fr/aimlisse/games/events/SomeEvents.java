package fr.aimlisse.games.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aimlisse.games.HGMain;
import fr.aimlisse.games.util.GameStatus;

public class SomeEvents  implements Listener {

	private HGMain main;

    public SomeEvents(HGMain hgMain) {
        this.main = hgMain;
    }
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	private void onSandFall(EntityChangeBlockEvent event) {
		if (event.getEntityType() == EntityType.FALLING_BLOCK && event.getTo() == Material.AIR) {
			if (event.getBlock().getType() == Material.SAND) {
				event.setCancelled(true);
				event.getBlock().getState().update(false, false);
			}
		}
	}


	@EventHandler
	public void onSpawn(EntitySpawnEvent event) {
		Entity entity = event.getEntity();
		if(entity.getType() != EntityType.PLAYER) {
			event.setCancelled(true);
		}
	}
	
	
}
