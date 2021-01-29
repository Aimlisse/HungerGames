package fr.aimlisse.games.events;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.aimlisse.games.HGMain;

public class ActualisationEvent implements Listener {

	private HGMain main;

	public ActualisationEvent(HGMain hgMain) {
		this.main = hgMain;
	}
	/*
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.isOnline() == false)
			return;
		if (this.main.isStarted() != true) {
			ScoreboardManager scoreboardmanager = Bukkit.getScoreboardManager();
			Scoreboard board = scoreboardmanager.getNewScoreboard();
			Objective obj = board.registerNewObjective("HG", "dummy");
			obj.setDisplayName(this.main.GameName);
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			Score adress = obj.getScore("pvp.aimlisse.fr");
			Score empty = obj.getScore("§7------------");
			empty.setScore(6);
			Score empty1 = obj.getScore("§7------------");
			empty1.setScore(2);
			Score empty2 = obj.getScore("§r");
			empty1.setScore(3);
			adress.setScore(1);
		
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this.main, () -> {
               
				Score onlinestxt = obj.getScore("§3");
                onlinestxt = obj.getScore("§r");
                onlinestxt = obj.getScore("§6Joueurs:§7 " + Bukkit.getOnlinePlayers().size());
				onlinestxt.setScore(5);
				player.setScoreboard(board);
				

			}, 100L, 100L);

		} 

	}
	*/
}
