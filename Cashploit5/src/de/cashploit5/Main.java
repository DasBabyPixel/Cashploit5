package de.cashploit5;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.cashploit5.command.CommandManager;

public class Main extends JavaPlugin {

	public static final String PREFIX = "§8[§aCash§6Ploit§95§8] §7";
	
	private CommandManager manager = new CommandManager();
	
	public CommandManager getCommandManager() {
		return manager;
	}
	
	public static void sendMessage(Player p, String message) {
		p.sendMessage(PREFIX + message);
	}
	
	public static Main getPlugin() {
		return Cashploit.getPlugin();
	}
}
