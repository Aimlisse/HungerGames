package fr.aimlisse.games.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.aimlisse.games.HGMain;

public class SpecCommand implements CommandExecutor {
	private HGMain main;

	public SpecCommand(HGMain hgMain) {
		this.main = hgMain;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("admin.spec")) {
				if(this.main.spectators.contains(player)) {
					player.getInventory().clear();
					this.main.spectators.remove(player);
					player.sendMessage("§cVous n'êtes plus spectateur.");
					player.getActivePotionEffects().clear();
					player.removePotionEffect(PotionEffectType.INVISIBILITY);
				}else {
					this.main.setSpectator(player);
					player.sendMessage("§aVous êtes désormais spectateur.");
				}
			}else {
				player.sendMessage(this.main.ErrorPrefix + "Cette commande est réservée aux administrateurs/développeurs.");
			}
		}else {
			sender.sendMessage("§cDésactivé pour la console !");
		}
		return true;
	}

}
