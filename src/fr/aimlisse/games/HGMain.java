package fr.aimlisse.games;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.aimlisse.games.commands.KitsCommand;
import fr.aimlisse.games.commands.SpawnCommand;
import fr.aimlisse.games.commands.SpecCommand;
import fr.aimlisse.games.commands.StartCommand;
import fr.aimlisse.games.events.ActualisationEvent;
import fr.aimlisse.games.events.ClickEvent;
import fr.aimlisse.games.events.EndEvent;
import fr.aimlisse.games.events.JoinEvent;
import fr.aimlisse.games.events.KitsEvent;
import fr.aimlisse.games.events.LeaveEvent;
import fr.aimlisse.games.events.SomeEvents;
import fr.aimlisse.games.events.SpectatorEvent;
import fr.aimlisse.games.util.GameStatus;
import fr.aimlisse.games.util.User;

/* CHOSES A AJOUTER/FAIRE
 * VARIABLE GAME EST DEMARREE OU NON (EN DEV)
 */
public class HGMain extends JavaPlugin {

	public static final long DefaultTime = 0;
	public boolean started;
	private GameStatus status = GameStatus.WAITING;
	public List<Player> invincible = new ArrayList<>();
	public List<Player> spectators = new ArrayList<>();
	public List<Player> inGame = new ArrayList<>();
	public List<Chest> chests = new ArrayList<>();
	public List<Player> basiscs = new ArrayList<>();
	public List<Player> wereWolfs = new ArrayList<>();
	public List<Player> sangsues = new ArrayList<>();

	public GameStatus getStatus() {
		return status;
	}

	private void gameStart1() {
		ItemStack blocks = new ItemStack(Material.DIRT, 16);
		ItemStack sword = new ItemStack(Material.WOOD_SWORD);

		Location tp1 = new Location(Bukkit.getWorld("world"), 2.5, 112.0, 17.5, 170, 0);
		Location tp2 = new Location(Bukkit.getWorld("world"), -2.5, 112.0, 17.5, -170, 0);

		Location tp3 = new Location(Bukkit.getWorld("world"), -17.5, 112.0, 2.5, -100, 0);
		Location tp4 = new Location(Bukkit.getWorld("world"), -17.5, 112.0, -2.5, -80, 0);

		Location tp5 = new Location(Bukkit.getWorld("world"), -2.5, 112.0, -17.5, -10, 0);
		Location tp6 = new Location(Bukkit.getWorld("world"), 2.5, 112.0, -17.5, 10, 0);

		Location tp7 = new Location(Bukkit.getWorld("world"), 17.5, 112.0, -2.5, 80, 0);
		Location tp8 = new Location(Bukkit.getWorld("world"), 17.5, 112.0, 2.5, 100, 0);
		int loc = 0;
		for (Player playersOnline : Bukkit.getOnlinePlayers()) {
			loc = loc + 1;
			if (loc == 1) {
				playersOnline.teleport(tp1);
			} else if (loc == 2) {
				playersOnline.teleport(tp2);
			} else if (loc == 3) {
				playersOnline.teleport(tp3);
			} else if (loc == 4) {
				playersOnline.teleport(tp4);
			} else if (loc == 5) {
				playersOnline.teleport(tp5);
			} else if (loc == 6) {
				playersOnline.teleport(tp6);
			} else if (loc == 7) {
				playersOnline.teleport(tp7);
			} else if (loc == 8) {
				playersOnline.teleport(tp8);
			} else {
				playersOnline.kickPlayer(ErrorPrefix + "Une erreur s'est produite lors de la téléportation !\"");
			}
			playersOnline.setAllowFlight(false);
			playersOnline.setGameMode(GameMode.SURVIVAL);
			playersOnline.getInventory().clear();
			playersOnline.getInventory().setItem(0, sword);
			playersOnline.getInventory().setItem(8, blocks);
			// playersOnline.teleport(new
			// Location(Bukkit.getWorld(getConfig().getString("aimlissehg.spawn-world")),
			// getConfig().getDouble("aimlissehg.spawn-x"),
			// getConfig().getDouble("aimlissehg.spawn-y"),
			// getConfig().getDouble("aimlissehg.spawn-z")));
			playersOnline.sendMessage(teleportedMessage);
			// invincible.add(playersOnline);

		}

		setStatus(GameStatus.IN);
	}

	public void gameStart(Boolean force) {
		setStatus(GameStatus.STARTING);
		Bukkit.broadcastMessage(this.GameStartBroadcast + "§b5 secondes");
		Bukkit.getScheduler().runTaskLater(this, () -> {
			Bukkit.broadcastMessage("§6● §eLa partie commence ! §b§lGL ALL!");
			int players = 0;
			for (Player playersOnline : Bukkit.getOnlinePlayers()) {
				if (!playersOnline.getActivePotionEffects().contains(PotionEffectType.INVISIBILITY)) {
					++players;
				}
			}
			if (players >= 8) {
				for (Player playersOnline : Bukkit.getOnlinePlayers()) {
					gameStart1();
				}
			} else {
				if (force == false) {
					Bukkit.broadcastMessage(cancelledMessage);
				} else {
					gameStart1();
				}
			}
		}, 100L);
	}

	// DEFINIR LES SPECTATEURS
	public void setSpectator(Player player) {
		this.spectators.add(player);
		player.setGameMode(GameMode.ADVENTURE);
		player.setAllowFlight(true);
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2, true));
		for (Player inGame : Bukkit.getOnlinePlayers()) {
			inGame.hidePlayer(player);
		}
		player.getInventory().clear();
		player.getActivePotionEffects().clear();
		player.setHealth(20);
		ItemStack Teleport = new ItemStack(Material.COMPASS);
		ItemMeta TP1 = Teleport.getItemMeta();
		TP1.setDisplayName(SpecTeleportItem + itemRightClickText);
		Teleport.setItemMeta(TP1);
		player.getInventory().setItem(0, Teleport);

		ItemStack leave = new ItemStack(Material.WOOD_DOOR);
		ItemMeta leave1 = leave.getItemMeta();
		leave1.setDisplayName(this.BackToHubItemStackName + this.itemRightClickText);
		leave1.setLore(this.BackToHubLore);
		leave.setItemMeta(leave1);
		player.getInventory().setItem(8, leave);
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public static Map<UUID, User> players = new HashMap<>();

	public Boolean isBetaMode = false;
	public String BetaItemStackName = "§c§lParamètres Bêta";

	public Location spawn = new Location(Bukkit.getWorld("world"), 0, 110, 0, 0, 0);

	public String SpecTeleportItem = "§aTéléportation à un joueur";

	public String KitMenuName = "§eHungerGames §7» §bKits";
	public String teleportedMessage = "§eHungerGames §7➜ §aVous avez été téléporté ! Bon jeu !";

	public String KitItemStackName = "§eMenu des Kits";
	public String BackToHubItemStackName = "§cRetour au lobby";

	public String cancelledMessage = "§6● §c§lDEMARRAGE IMPOSSIBLE! §cPas assez de joueurs.";

	public String GameStartBroadcast = "§6● §eLa partie commence dans §b";
	public String ErrorPrefix = "§c§lErreur ➜ §c ";
	public String GameName = "§9§lHG BETA";
	public String GameDescription = "§eBienvenue en HungerGames !";
	public String itemRightClickText = "§7 ● §eClic Droit";

	public List<String> kitsItemLore = Arrays.asList("§eChoisissez votre kit de départ",
			"§eParmi ceux que vous avez débloqué !");
	public List<String> BackToHubLore = Arrays.asList("§eClic Droit pour retourner", "§eAu lobby.");

	public void onEnable() {
		Logger.getLogger("Minecraft").info("[HungerGames] Démarrage du plugin...");
		getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
		getServer().getPluginManager().registerEvents(new ClickEvent(this), this);
		getServer().getPluginManager().registerEvents(new LeaveEvent(this), this);
		getServer().getPluginManager().registerEvents(new SomeEvents(this), this);
		getServer().getPluginManager().registerEvents(new SpectatorEvent(this), this);
		getServer().getPluginManager().registerEvents(new ActualisationEvent(this), this);
		getServer().getPluginManager().registerEvents(new EndEvent(this), this);
		getServer().getPluginManager().registerEvents(new KitsEvent(this), this);
		getCommand("kits").setExecutor(new KitsCommand(this));
		getCommand("spec").setExecutor(new SpecCommand(this));
		getCommand("start").setExecutor(new StartCommand(this));
		getCommand("spawn").setExecutor(new SpawnCommand(this));
		setStarted(false);
		setStatus(GameStatus.WAITING);
		saveDefaultConfig();
		List<Material> items = new ArrayList<>();

		for (Chunk c : Bukkit.getWorld("world").getLoadedChunks()) {
			for (BlockState b : c.getTileEntities()) {
				if (b instanceof Chest) {

					Chest chest = (Chest) b;
					Inventory inventory = chest.getBlockInventory();
					if (!chests.contains(chest)) {
						Material[] randomItens = { Material.AIR, Material.LAVA_BUCKET, Material.GOLDEN_APPLE,
								Material.COOKED_BEEF, Material.DIAMOND_CHESTPLATE, Material.IRON_HELMET,
								Material.IRON_BOOTS, Material.STICK, Material.STONE_SWORD, Material.WOOD_SWORD,
								Material.STONE_AXE, Material.DIAMOND_PICKAXE, Material.ARROW, Material.BOW,
								Material.WOOD, Material.DEAD_BUSH, Material.BONE, Material.CAKE, Material.COOKED_RABBIT,
								Material.FISHING_ROD, Material.WATER_BUCKET, Material.TNT, Material.EXP_BOTTLE,
								Material.FLINT_AND_STEEL, Material.LOG, Material.COBBLESTONE };
						chests.add(chest);
						Random numGen = new Random();
						int randC = Math.abs((7) + numGen.nextInt(19));
						for (int i = 0; i < randC; i++) {
							Random rand = new Random();

							int max = 14;
							for (int amountOfItems = 3; amountOfItems < max; amountOfItems++) {
								inventory.setItem(randC, new ItemStack(randomItens[rand.nextInt(randomItens.length)]));
							}
						}

					}
				}

			}
		}

	}

	public void onDisable() {
		setStatus(GameStatus.WAITING);
		File regen = new File("/world1");
		regen.getName().equalsIgnoreCase("/world");
		chests.clear();
	}

	public void giveKits() {
		if (started == false)
			return;
		if (status != GameStatus.IN)
			return;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (wereWolfs.contains(player)) {

				player.getInventory().setItem(0, new ItemStack(Material.STONE_AXE));
			}
			if (basiscs.contains(player)) {
				ItemStack  bread = new ItemStack(Material.BREAD);
				bread.setAmount(16);
				player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
				player.getInventory().setItem(1, new ItemStack(Material.WOOD_AXE));
				player.getInventory().setItem(2, new ItemStack(Material.WOOD_PICKAXE));
				player.getInventory().setItem(8, bread);
				
			}
			if (sangsues.contains(player)) {
				player.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
			}
		}

	}

}
