package de.cashploit5.v1_12_2.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.cashploit5.Main;
import de.cashploit5.v1_12_2.uuidmanager.UUIDManager;

public class FreezeCommand extends Command implements Listener {

	public FreezeCommand() {
		super("freeze");
		Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
	}

	private List<String> freezedPlayers = new ArrayList<>();

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if (args.length == 1) {
			Player target = UUIDManager.getPlayer(args[0]);
			if (target == null) {
				Main.sendMessage(p, "§cDieser Spieler ist nicht online!");
			} else {
				if (freezedPlayers.contains(target.getUniqueId().toString())) {
					freezedPlayers.remove(target.getUniqueId().toString());
					Main.sendMessage(p, "§cSpieler §6" + target.getName() + " §cwurde entfreezed");
				} else {
					freezedPlayers.add(target.getUniqueId().toString());
					Main.sendMessage(p, "§cSpieler §6" + target.getName() + " §cwurde gefreezed");
				}
			}
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (freezedPlayers.contains(p.getUniqueId().toString())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (freezedPlayers.contains(p.getUniqueId().toString())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (freezedPlayers.contains(p.getUniqueId().toString())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (freezedPlayers.contains(p.getUniqueId().toString())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();
		if (freezedPlayers.contains(p.getUniqueId().toString())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (freezedPlayers.contains(p.getUniqueId().toString())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if (freezedPlayers.contains(p.getUniqueId().toString())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamage1(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if (freezedPlayers.contains(p.getUniqueId().toString())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByBlockEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if (freezedPlayers.contains(p.getUniqueId().toString())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player)e.getWhoClicked();
			if (freezedPlayers.contains(p.getUniqueId().toString())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player ) {
			Player p = (Player)e.getDamager();
			if (freezedPlayers.contains(p.getUniqueId().toString())) {
				e.setCancelled(true);
			}
		}
	}
}
