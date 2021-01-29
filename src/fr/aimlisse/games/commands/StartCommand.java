package fr.aimlisse.games.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aimlisse.games.HGMain;
import fr.aimlisse.games.util.GameStatus;

public class StartCommand implements CommandExecutor {
	private HGMain main;

	public StartCommand(HGMain hgMain) {
		this.main = hgMain;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("admin.game.start")) {
				if(this.main.getStatus() == GameStatus.IN) {
					player.sendMessage(this.main.ErrorPrefix + "Cette partie est déjà lancée.");
				}else {
					player.sendMessage("§aDémarrage §bforcée §ade la partie...");
					this.main.gameStart(true);
					this.main.setStatus(GameStatus.IN);
				}
			}
		}else {
			sender.sendMessage("§cNon disponible pour la console !");
		}
		return false;
	}

}
