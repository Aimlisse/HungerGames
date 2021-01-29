package fr.aimlisse.games.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aimlisse.games.HGMain;

public class LeaveEvent implements Listener {
	private HGMain main;

    public LeaveEvent(HGMain hgMain) {
        this.main = hgMain;
    }
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if(Bukkit.getOnlinePlayers().size() < 7) {
			this.main.setStarted(false);
			
		}

	}

}
