package de.cashploit5.command.tab;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.cashploit5.events.tab.TabCompleter;

public class PluginManagerCompleter implements TabCompleter {

	private List<String> tabs1 = new ArrayList<>();

	public PluginManagerCompleter() {
		tabs1.add("enable");
		tabs1.add("disable");
		tabs1.add("list");
	}

	@Override
	public List<String> onTab(Player p, String[] args) {
		List<String> tab = new ArrayList<>();
		Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
		List<String> pl = new ArrayList<>();
		for(Plugin plugin : plugins) {
			pl.add(plugin.getName());
		}
		if (args.length == 1) {
			String start = args[0];
			for (int i = 0; i < tabs1.size(); i++) {
				if (tabs1.get(i).toLowerCase().startsWith(start.toLowerCase()))
					tab.add(tabs1.get(i));
			}
		} else if(args.length == 2) {
			String start = args[1];
			for (int i = 0; i < pl.size(); i++) {
				if (pl.get(i).toLowerCase().startsWith(start.toLowerCase()))
					tab.add(pl.get(i));
			}
		}
		return tab;
	}

}
