package de.cashploit5;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.main.MainClass;

/**
 * 
 * @author Java-Lorenz
 */
	
public abstract class Main extends JavaPlugin {

	public static final String PREFIX = "§8[§aCash§6Ploit§95§8] §7";
	private static de.cashploit5.v1_8_8.command.CommandManager commandManager_v1_8;
	private static de.cashploit5.v1_12_2.command.CommandManager commandManager_v1_12;
	private static HashMap<YamlConfiguration, File> fileFromConfig = new HashMap<>();
	private static HashMap<String, YamlConfiguration> configFromName = new HashMap<>();
	
	public void setCommandManagerFor_v1_8_8(de.cashploit5.v1_8_8.command.CommandManager cmd) {
			commandManager_v1_8 = cmd;
	}
	
	public void setCommandManagerFor_v1_12_2(de.cashploit5.v1_12_2.command.CommandManager cmd) {
		commandManager_v1_12 = cmd;
	}
	
	public de.cashploit5.v1_8_8.command.CommandManager getCommandManager_v1_8_8() {
		return commandManager_v1_8;
	}
	
	public de.cashploit5.v1_12_2.command.CommandManager getCommandManager_v1_12_2() {
		return commandManager_v1_12;
	}
	
	public de.cashploit5.v1_8_8.command.Command getCashploitCommand_v1_8_8(String name) {
		for(de.cashploit5.v1_8_8.command.Command command : getCommandManager_v1_8_8().getCommands()) {
			if(command.getName().equalsIgnoreCase(name)) {
				return command;
			}
		}
		return null;
	}
	
	public de.cashploit5.v1_12_2.command.Command getCashploitCommand_v1_12_2(String name) {
		for(de.cashploit5.v1_12_2.command.Command command : getCommandManager_v1_12_2().getCommands()) {
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
