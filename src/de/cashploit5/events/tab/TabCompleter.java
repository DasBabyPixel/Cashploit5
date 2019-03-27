package de.cashploit5.events.tab;

import java.util.List;

import org.bukkit.entity.Player;

public interface TabCompleter  {
	
	List<String> onTab(Player p, String[] args);
	
}
