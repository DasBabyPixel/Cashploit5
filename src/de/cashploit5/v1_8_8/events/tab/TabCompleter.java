package de.cashploit5.v1_8_8.events.tab;

import java.util.List;

import org.bukkit.entity.Player;

public interface TabCompleter {

	public List<String> onTab(Player p, String[] args);
	
}
