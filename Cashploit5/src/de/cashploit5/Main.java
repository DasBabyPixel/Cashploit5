package de.cashploit5;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.cashploit5.command.CommandManager;

public class Main extends JavaPlugin {

	public static final String PREFIX = "§8[§aCash§6Ploit§95§8] §7";
	private static CommandManager commandManager;
	
	public void setCommandManager(CommandManager cmd) {
		commandManager = cmd;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
	
	public static void sendMessage(Player p, String message) {
		p.sendMessage(PREFIX + message);
	}
	
	public static Main getPlugin() {
		return Cashploit.getPlugin();
	}
}
