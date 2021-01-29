package fr.aimlisse.games.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aimlisse.games.HGMain;
import fr.aimlisse.games.util.GameStatus;

public class SpawnCommand implements CommandExecutor {

	private HGMain main;

	public SpawnCommand(HGMain hgMain) {
		this.main = hgMain;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(this.main.getStatus() != GameStatus.IN) {
				player.teleport(new Location(Bukkit.getWorld("world"), 0, 110, 0, 0, 0));
			}else {
				player.sendMessage(this.main.ErrorPrefix + "Cette commande est désactivée lorsque la partie est démarrée !");
			}
		}
		return true;
	}

}
