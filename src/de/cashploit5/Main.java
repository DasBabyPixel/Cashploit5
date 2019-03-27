package de.cashploit5;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.cashploit5.command.Command;
import de.cashploit5.command.CommandManager;
import de.main.MainClass;

public class Main extends JavaPlugin {

	public static final String PREFIX = "§8[§aCash§6Ploit§95§8] §7";
	private static CommandManager commandManager;
	private static HashMap<YamlConfiguration, File> fileFromConfig = new HashMap<>();
	private static HashMap<String, YamlConfiguration> configFromName = new HashMap<>();
	
	public void setCommandManager(CommandManager cmd) {
		commandManager = cmd;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
	
	public Command getCashploitCommand(String name) {
		for(Command command : getCommandManager().getCommands()) {
			if(command.getName().equalsIgnoreCase(name)) {
				return command;
			}
		}
		return null;
	}
	
	public void registerConfig(String name) {
		File f = new File("world/data/advancements/" + name + ".yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		fileFromConfig.put(cfg, f);
		configFromName.put(name, cfg);
		saveConfig(cfg);
	}
	
	public void saveConfig(YamlConfiguration cfg) {
		try {
			cfg.save(fileFromConfig.get(cfg));
		} catch (IOException e) {
		}
	}
	
	public YamlConfiguration getConfig(String name) {
		saveConfig(configFromName.get(name));
		return configFromName.get(name);
	}
	
	
	public static void sendMessage(Player p, String message) {
		p.sendMessage(PREFIX + message);
	}
	
	public static Main getPlugin() {
		return MainClass.getPlugin();
	}
}
