package fr.aimlisse.games.events;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import fr.aimlisse.games.HGMain;

public class EndEvent implements Listener {

	private HGMain main;

	public EndEvent(HGMain hgMain) {
		this.main = hgMain;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
       
	}


	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		 if(this.main.isStarted() == true) {
		for (Player players : Bukkit.getOnlinePlayers()) {
		
				if (!players.getActivePotionEffects().contains(PotionEffectType.INVISIBILITY)) {
					Bukkit.broadcastMessage("§d§l " + players.getDisplayName() + " §6§lRemporte la victoire !");
					Bukkit.getScheduler().runTaskLater(this.main, () -> {
						ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
						String command = "restart";
						Bukkit.dispatchCommand(console, command);
					}, 200L);
				
				}
			}
		
	}
}
}
