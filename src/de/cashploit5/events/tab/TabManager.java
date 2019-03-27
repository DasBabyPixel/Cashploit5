package de.cashploit5.events.tab;

import java.util.HashMap;

import de.cashploit5.command.Command;

public class TabManager {
	
	private static HashMap<Command,TabCompleter> tabCompleters = new HashMap<>();
	
	public static HashMap<Command,TabCompleter> getTabCompleters() {
		return tabCompleters;
	}
}
