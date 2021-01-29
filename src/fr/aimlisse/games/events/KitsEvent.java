package fr.aimlisse.games.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.aimlisse.games.HGMain;

public class KitsEvent implements Listener {
	
	private HGMain main;
	public KitsEvent(HGMain hgMain) {
	this.main = hgMain;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		if(main.wereWolfs.contains(killer)) {
			killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 0));
			killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0));
		}
		if(main.sangsues.contains(killer)) {
			killer.setHealth(killer.getHealth() + 8);
		}
	}
}
