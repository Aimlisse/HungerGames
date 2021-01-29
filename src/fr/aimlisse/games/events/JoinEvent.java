package fr.aimlisse.games.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import fr.aimlisse.games.HGMain;
import fr.aimlisse.games.util.GameStatus;
import fr.aimlisse.games.util.User;

public class JoinEvent implements Listener {
	private HGMain main;
	
	public JoinEvent(HGMain hgMain) {
		this.main = hgMain;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();
        player.getActivePotionEffects().clear();
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
		player.sendMessage("§6§m-----------------------------");
		player.sendMessage(" §9Aimlisse §7- §6" + this.main.GameName);
		;
		player.sendMessage(" ");
		player.sendMessage(this.main.GameDescription);
		player.sendMessage("§6§m-----------------------------");
		User user = new User(player);
		player.teleport(new Location(Bukkit.getWorld("world"), 0, 110, 0, 0, 0));
		player.getInventory().clear();
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setLevel(0);
		player.setExp(0);
		player.setPlayerWeather(WeatherType.CLEAR);
		player.setGameMode(GameMode.ADVENTURE);
		player.setAllowFlight(true);
		player.setPlayerTime(this.main.DefaultTime, false);
		player.getInventory().setHeldItemSlot(0);
		player.getActivePotionEffects().clear();
		ItemStack kit = new ItemStack(Material.BOW);
		ItemMeta kit1 = kit.getItemMeta();
		kit1.setDisplayName(this.main.KitItemStackName + this.main.itemRightClickText);
		kit1.setLore(this.main.kitsItemLore);
		kit.setItemMeta(kit1);
		player.getInventory().setItem(0, kit);

		if(this.main.isBetaMode == true) {
			ItemStack betaSettings = new ItemStack(Material.DIODE);
			ItemMeta betaSettings1 = betaSettings.getItemMeta();
			betaSettings1.setDisplayName(this.main.BetaItemStackName + this.main.itemRightClickText);
			betaSettings1.setLore(this.main.kitsItemLore);
			betaSettings.setItemMeta(betaSettings1);
			player.getInventory().setItem(4, betaSettings);
		}
		
		ItemStack leave = new ItemStack(Material.NETHER_STAR);
		ItemMeta leave1 = leave.getItemMeta();
		leave1.setDisplayName(this.main.BackToHubItemStackName + this.main.itemRightClickText);
		leave1.setLore(this.main.BackToHubLore);
		leave.setItemMeta(leave1);
		player.getInventory().setItem(8, leave);
		this.main.inGame.add(player);
        player.setStatistic(Statistic.PLAYER_KILLS, 0);
		int players = 0;
		for (Player playersOnline : Bukkit.getOnlinePlayers()) {
			if(!playersOnline.getActivePotionEffects().contains(PotionEffectType.INVISIBILITY)) {
				++players;
				if(players >= 8) {
					this.main.gameStart(true);
				}
			}
		}
		if(players >= 8) {
			this.main.gameStart(false);
		}
}
}
