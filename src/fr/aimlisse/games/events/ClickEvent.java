package fr.aimlisse.games.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.aimlisse.games.HGMain;
import fr.aimlisse.games.util.GameStatus;

public class ClickEvent implements Listener {
	private HGMain main;

	public ClickEvent(HGMain hgMain) {
		this.main = hgMain;
	}
	@EventHandler
	public void OnInventoryClick(InventoryClickEvent event){
		Inventory inv = event.getInventory();
		ItemStack item = event.getCurrentItem();
		if(item == null) return;
		Player p = (Player) event.getWhoClicked();
		if(p.getGameMode() != GameMode.CREATIVE) {
			if(this.main.getStatus() != GameStatus.IN) {
				event.setCancelled(true);
			}
		}
		if(inv.getName().equalsIgnoreCase(this.main.KitMenuName)) {
			event.setCancelled(true);
			if(item.getItemMeta().getDisplayName().contains("Fermer")) {
				p.closeInventory();
			}
		}
	}
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(player.getGameMode() == GameMode.ADVENTURE) {
			event.setCancelled(true);
		}
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack item = event.getItem();
		if(action == null) return;
		if(action == Action.RIGHT_CLICK_BLOCK) {
			if(player.getGameMode() != GameMode.CREATIVE) {
				if(this.main.getStatus() != GameStatus.IN) {
					event.setCancelled(true);
				}
			}
		}
		if(item == null) return;
		if(item.getType() == Material.AIR) return;
		if(item.getItemMeta().getDisplayName() == null) return;
		if(item.getItemMeta().getDisplayName().contains(this.main.KitItemStackName)) {
			event.setCancelled(true);
			player.performCommand("kits");
		}else if(item.getItemMeta().getDisplayName().contains(this.main.BackToHubItemStackName)) {
			event.setCancelled(true);
			player.performCommand("lobby");
		}
	}
}
