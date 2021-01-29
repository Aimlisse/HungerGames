package fr.aimlisse.games.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.aimlisse.games.HGMain;
import fr.aimlisse.games.util.GameStatus;


public class KitsCommand implements CommandExecutor {
	
	private HGMain main;

	public KitsCommand(HGMain hgMain) {
		this.main = hgMain;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(this.main.getStatus() != GameStatus.IN) {
				if(args.length == 0) {
					Inventory kits = Bukkit.createInventory(null, 54, this.main.KitMenuName);
					ItemStack close = new ItemStack(Material.BARRIER);
					ItemMeta close1 = close.getItemMeta();
					close1.setDisplayName("§c§lFermer");
					close.setItemMeta(close1);
					kits.setItem(49, close);
					
					
					ItemStack default1 = new ItemStack(Material.STONE_PICKAXE);
					ItemMeta default11 = default1.getItemMeta();
					default11.setDisplayName("§eKit de base §a§lSELECTIONNE");
					default1.setItemMeta(default11);
					kits.setItem(47, default1);
					
					player.openInventory(kits);
				}if(args[0].equalsIgnoreCase("basique")) {
				main.basiscs.add(player);
				main.wereWolfs.remove(player);
				main.sangsues.remove(player);
			}if(args[0].equalsIgnoreCase("lg")) {
				main.wereWolfs.add(player);
				main.basiscs.remove(player);
				main.sangsues.remove(player);
			}if(args[0].equalsIgnoreCase("sangsue")) {
				main.sangsues.add(player);
				main.basiscs.remove(player);
				main.wereWolfs.remove(player);
			}
			}else {
				player.sendMessage(this.main.ErrorPrefix + "Il n'est plus possible de choisir un kit quand la partie est déjà lancée !");
			}
			
		}else {
			sender.sendMessage("§cCette commande n'est pas disponible pour la console !");
		}
		return true;
	}

}
